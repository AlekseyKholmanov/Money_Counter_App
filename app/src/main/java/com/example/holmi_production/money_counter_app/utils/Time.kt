package com.example.holmi_production.money_counter_app.utils

import android.util.Log
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.*

object Time {
    fun getDiffToNextDay(addMinutes:Int = 0): Long {
        val now = DateTime.now()
        val nexDay = DateTime.now().withTimeAtStartOfDay().plusDays(1).plusMinutes(addMinutes)
        Log.d("M_Time","$nexDay")
        val duration = Duration(nexDay,now).abs().millis
        Log.d("M_Time","duration milis: $duration")
        return Duration(nexDay,now).abs().millis
    }
}

val currentTime: Long
    get() = Calendar.getInstance().timeInMillis