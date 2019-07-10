package com.example.holmi_production.money_counter_app.extensions

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

fun Number.toCurencyFormat(): String {
    return if (this.toString().takeLast(2) == ".0")
        (this.toString().dropLast(2))
    else
        String.format(Locale.ROOT,"%.1f", this)
}

fun DateTime.toRUformat(): String {
    val formatter = DateTimeFormat.forPattern("dd MMMM yyyy")
        .withLocale(Locale("ru"))
    return formatter.print(this)
}

fun Float.toDateFormat():String{
    val date = DateTime().withDayOfYear(this.toInt())
    return date.toRUformat()
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