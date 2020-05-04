package com.example.holmi_production.money_counter_app.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.async(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}

fun <T> Flowable<T>.async(): Flowable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.async(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.subscribeOnIo(): Flowable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.observeOnUi(delayError: Boolean = true): Flowable<T> {
    return observeOn(AndroidSchedulers.mainThread(), delayError)
}

fun Completable.async(): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.complete(): Disposable {
    return async().subscribe()
}

fun <T> Maybe<T>.async(): Maybe<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}