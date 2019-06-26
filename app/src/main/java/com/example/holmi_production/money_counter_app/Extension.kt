package com.example.holmi_production.money_counter_app

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bottom_keyboard.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*





inline fun <T : Any, K : Comparable<K>> Observable<T>.sortedByDescending(crossinline keySelector: (T) -> K): Observable<T> {
    return sorted { left, right -> keySelector(right).compareTo(keySelector(left)) }
}
