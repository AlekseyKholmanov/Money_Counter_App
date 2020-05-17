package com.example.holmi_production.money_counter_app.ui.fragments

import android.util.Log
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.main.BaseFragment

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class KeyboardPartFragment: BaseFragment(R.layout.part_fragment_keyboard_const) {
    override fun inject() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("M_KeyboardPartFragment","on destroy view")
    }
}