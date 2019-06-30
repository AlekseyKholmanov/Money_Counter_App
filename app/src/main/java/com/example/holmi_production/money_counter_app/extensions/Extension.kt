package com.example.holmi_production.money_counter_app.extensions

import io.reactivex.Observable
import org.joda.time.DateTime

inline fun <T : Any, K : Comparable<K>> Observable<T>.sortedByDescending(crossinline keySelector: (T) -> K): Observable<T> {
    return sorted { left, right -> keySelector(right).compareTo(keySelector(left)) }
}
 fun Long.toDateTime(): DateTime {
     return DateTime(this)
 }
