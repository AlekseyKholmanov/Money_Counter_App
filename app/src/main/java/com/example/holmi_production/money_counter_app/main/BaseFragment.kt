package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.bindView
import com.example.holmi_production.money_counter_app.ui.MainActivity
import com.example.holmi_production.money_counter_app.ui.custom.CustomBackgroundDrawable
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val background = CustomBackgroundDrawable()
        requireActivity().window.setBackgroundDrawable(background)
    }
}