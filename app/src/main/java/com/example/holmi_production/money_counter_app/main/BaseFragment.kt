package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.extensions.bindView
import moxy.MvpAppCompatFragment

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
abstract class BaseFragment(private val layoutId: Int) : MvpAppCompatFragment() {

    private val appbarConfig
        get() = (requireActivity() as MainActivity).appBarConfig

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    protected fun initToolbar(@StringRes titleRes: Int): Toolbar {
        return initToolbar(requireContext().getString(titleRes))
    }

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