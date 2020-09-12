package com.example.holmi_production.money_counter_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.example.holmi_production.money_counter_app.R
import kotlinx.android.synthetic.main.activity_main.*

class OnboardingActivity :AppCompatActivity(){

    val navController by lazy {
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        host.navController
    }

    val appBarConfiguration: AppBarConfiguration by lazy(LazyThreadSafetyMode.NONE) {
        AppBarConfiguration(topLevelDestinations, null) { false }
    }

    val topLevelDestinations: Set<Int>
        get() = setOf(
            R.id.onBoardingFragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        initNavController()
    }

    private fun initNavController() {
        val mainGraph = navController.navInflater.inflate(R.navigation.onboarding_graph)
        navController.graph = mainGraph
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}