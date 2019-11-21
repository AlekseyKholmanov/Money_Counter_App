package com.example.holmi_production.money_counter_app.extensions

import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import java.lang.Exception
import java.util.*

fun Number.toCurencyFormat(): String {
    return if (this.toString().takeLast(2) == ".0")
        (this.toString().dropLast(2))
    else
        String.format(Locale.ROOT, "%.1f", this)
}

fun DateTime.getNameDayOfWeek(): String {
    return when (this.dayOfWeek().get()) {
        1 -> "Понедельник"
        2 -> "Вторник"
        3 -> "Среда"
        4 -> "Четверг"
        5 -> "Пятница"
        6 -> "Суббота"
        7 -> "Воскресение"
        else ->{
            throw Exception("некорректная дата ")
        }
    }

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

fun String.withRubleSign(): String {
    return "${this} \u20BD"
}