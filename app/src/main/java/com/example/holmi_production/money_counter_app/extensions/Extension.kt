package com.example.holmi_production.money_counter_app.extensions

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import io.reactivex.Observable
import org.joda.time.DateTime

inline fun <T : Any, K : Comparable<K>> Observable<T>.sortedByDescending(crossinline keySelector: (T) -> K): Observable<T> {
    return sorted { left, right -> keySelector(right).compareTo(keySelector(left)) }
}
 fun Long.toDateTime(): DateTime {
     return DateTime(this)
 }

fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
    if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")

    (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
        ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
}
