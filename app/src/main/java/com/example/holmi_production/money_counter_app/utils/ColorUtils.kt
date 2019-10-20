package com.example.holmi_production.money_counter_app.utils

import android.graphics.Color
import kotlin.random.Random

object ColorUtils {
    fun getColor(): Int {
        val r = Random.nextInt(255)
        val g = Random.nextInt(255)
        val b = Random.nextInt(255)
        return Color.rgb(r, g, b)
    }
}