package com.example.holmi_production.money_counter_app

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

fun <T> Single<T>.async(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}

fun <T> Flowable<T>.async(): Flowable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.async(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.async(): Maybe<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Number.toCurencyFormat(): String {
    return if (this.toString().takeLast(2) == ".0")
        (this.toString().dropLast(2))
    else
        String.format("%.1f", this)
}

fun DateTime.toRUformat(): String {
    val formatter = DateTimeFormat.forPattern("dd MMMM yyyy")
        .withLocale(Locale("ru"))
    return formatter.print(this)

}

fun Int.getDayAddition(): String {
    val last = this % 10
    val mod = this % 100
    val end: String
    end = if (last == 1 && mod != 11)
        "день"
    else
        if ((last < 5 && last != 0)
            && (mod < 10 || mod > 20)
        )
            "дня"
        else
            "дней"
    return "$this $end"
}

inline fun <T : Any, K : Comparable<K>> Observable<T>.sortedByDescending(crossinline keySelector: (T) -> K): Observable<T> {
    return sorted { left, right -> keySelector(right).compareTo(keySelector(left)) }
}
