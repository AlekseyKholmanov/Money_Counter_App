package com.example.holmi_production.money_counter_app

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator


class Vibrator (private val v: Vibrator) {

    fun vibrate(milliseconds: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(milliseconds)
        }
    }
}