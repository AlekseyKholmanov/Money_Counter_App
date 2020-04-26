package com.example.holmi_production.money_counter_app.main

import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class Sidebar(
    private var _sidebar: View?,
    private var _activity: AppCompatActivity?
) {

    private val sidebar
        get() = requireNotNull(_sidebar)

    private val activity
        get() = requireNotNull(_activity)

    fun onDestroy() {
        _sidebar = null
        _activity = null
    }

}