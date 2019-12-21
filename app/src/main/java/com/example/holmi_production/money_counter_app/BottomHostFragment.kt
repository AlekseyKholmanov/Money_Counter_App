package com.example.holmi_production.money_counter_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_bottom_container.*

class BottomHostFragment : AndroidXMvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(requireActivity(), R.id.bottomNavFragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.categoryPickerFragment -> {
                    hideBottomNav()
                    hideTopbar()
                }
                R.id.mainFragment -> {
                    hideTopbar()
                    showBottomNav()
                }
                R.id.settingsFragment ->{
                    showBottomNav()
                    hideTopbar()
                }
                R.id.categoryPickerWithCreateFragment -> {
                    hideTopbar()
                    hideBottomNav()
                }
                else -> {
                    showBottomNav()
                    showTopbar()
                }
            }
        }
        bottom_navigation.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        bottom_navigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottom_navigation.visibility = View.GONE
    }

    private fun hideTopbar() {
        topbarContainer.visibility = View.GONE
    }

    private fun showTopbar() {
        topbarContainer.visibility = View.VISIBLE
    }
}