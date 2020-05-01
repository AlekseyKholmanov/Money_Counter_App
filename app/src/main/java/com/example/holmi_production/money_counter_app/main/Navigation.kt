package com.example.holmi_production.money_counter_app.main

import androidx.fragment.app.Fragment

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
interface Navigation {
    fun loadFragment(
        fragment: Fragment,
        isAddedToBackstack: Boolean = false,
        withTopbar: Boolean = true,
        withBottomBar: Boolean = true,
        withAppBar: Boolean = false
    )
}