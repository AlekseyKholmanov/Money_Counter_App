package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.holmi_production.money_counter_app.ui.custom.CustomBackgroundDrawable
import com.example.holmi_production.money_counter_app.utils.Point


/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val displayMetrics = android.graphics.Point()
        requireActivity().display?.getRealSize(displayMetrics)
        val height = displayMetrics.y
        val width = displayMetrics.x
        val background = CustomBackgroundDrawable(width, height)
        requireActivity().window.setBackgroundDrawable(background)
    }
}