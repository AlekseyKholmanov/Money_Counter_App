package com.example.holmi_production.money_counter_app

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.holmi_production.money_counter_app.chart.ChartFragment
import com.example.holmi_production.money_counter_app.costs.CostsFragment
import com.example.holmi_production.money_counter_app.main.MainFragment
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment

class MainActivity : AppCompatActivity() {
    companion object {
        val FIRST_OPEN = "FirstOpen"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.mainNavFragment)
        val graph = navController.graph
        val preferences = this.getSharedPreferences("PREFERENCE_STORAGE", Context.MODE_PRIVATE)
        if (!preferences.contains(FIRST_OPEN))
            graph.startDestination = R.id.navFirstLaunch
        navController.graph = graph

    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()

}
