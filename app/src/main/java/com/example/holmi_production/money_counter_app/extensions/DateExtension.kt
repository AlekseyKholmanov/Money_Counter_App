package com.example.holmi_production.money_counter_app.extensions

import org.joda.time.DateTime
import java.util.*

fun DateTime.withTimeAtEndOfMonth(): DateTime {
    return when (val currentMonth = this.monthOfYear) {
        1, 3, 5, 7, 8, 10, 12 -> this.withMonthOfYear(currentMonth).withDayOfMonth(31)
        2 -> this.withMonthOfYear(currentMonth).withDayOfMonth(28)
        4, 6, 9, 11 -> this.withMonthOfYear(currentMonth).withDayOfMonth(30)
        else -> throw Exception("unknown month")
    }
}

fun DateTime.withTimeAtEndOfYear(): DateTime {
    val year = this.year().get()
    val isLeapYear = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
    return this.withDayOfYear(if (isLeapYear) 366 else 365)
}

fun DateTime.withTimeAtEndOfDay(): DateTime {
    return this.withTime(23, 59, 59, 999)
}

fun DateTime.withTimeAtStartOfMonth(): DateTime {
    return this.withDayOfMonth(1)
}

fun DateTime.withPreviousMonthDate(): DateTime {
    val month = this.monthOfYear
    val year = this.year
    return if (month == 1) this.withMonthOfYear(12).withYear(year - 1) else this.withMonthOfYear(
        month - 1
    )
}

fun DateTime.withNextMonthDate(): DateTime {
    val month = this.monthOfYear
    val year = this.year
    return if (month == 12) this.withMonthOfYear(1).withYear(year + 1) else this.withMonthOfYear(
        month + 1
    )
}