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

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    private val appbarConfig
        get() = (requireActivity() as MainActivity).appBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
//        inject()
        super.onCreate(savedInstanceState)
    }

    protected fun initToolbar(@StringRes titleRes: Int): Toolbar {
        return initToolbar(requireContext().getString(titleRes))
    }

    abstract fun inject()

    protected fun initToolbar(title: String = ""): Toolbar {
        val toolbar: Toolbar by bindView(R.id.toolbar)
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(title.isNotEmpty())
            if (title.isNotEmpty()) {
                setTitle(title)
            }
        }
        NavigationUI.setupWithNavController(
            toolbar,
            findNavController(),
            appbarConfig
        )
        return toolbar
    }
}