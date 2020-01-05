package com.example.holmi_production.money_counter_app.extensions

import org.joda.time.DateTime

fun DateTime.withTimeAtEndOfMonth(month: Int): DateTime {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> this.withMonthOfYear(month).withDayOfMonth(31)
        2 -> this.withMonthOfYear(month).withDayOfMonth(28)
        4, 6, 9, 11 -> this.withMonthOfYear(month).withDayOfMonth(30)
        else -> throw Exception("unknown month")
    }
}

fun DateTime.withTimeAtEndOfDay(): DateTime {
    return this.withTime(23, 59, 59, 999)
}

fun DateTime.withTimeAtStartOfMonth():DateTime{
    return this.withDayOfMonth(1)
}

fun DateTime.getDateWithPreviousMonth(): DateTime {
    val month = this.monthOfYear
    val year = this.year
    return if (month == 1) this.withMonthOfYear(12).withYear(year-1) else this.withMonthOfYear(month-1)
}

fun DateTime.getDateWithNextMonth(): DateTime {
    val month = this.monthOfYear
    val year = this.year
    return if (month == 12) this.withMonthOfYear(1).withYear(year+1) else this.withMonthOfYear(month+1)
}