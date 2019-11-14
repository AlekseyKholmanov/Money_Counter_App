package com.example.holmi_production.money_counter_app.utils

import org.joda.time.DateTime
import org.joda.time.Duration

object Time {
    fun getDiffToNextDay(addMinutes:Int = 0): Long {
        val now = DateTime.now()
        val nexDay = DateTime.now().withTimeAtStartOfDay().plusDays(1).plusMinutes(addMinutes)
        return Duration(nexDay,now).abs().millis
    }
}