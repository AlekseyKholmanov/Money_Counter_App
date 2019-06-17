package com.example.holmi_production.money_counter_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.holmi_production.money_counter_app.chart.ChartFragment
import com.example.holmi_production.money_counter_app.costs.CostsFragment
import com.example.holmi_production.money_counter_app.firstLaunch.FirstActivity
import com.example.holmi_production.money_counter_app.main.MainFragment
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val STORAGE_NAME = "StorageName"
        val FIRST_OPEN = "FirstOpen"
        val START_PERIOD = "START_PERIOD"
        val END_PERIOD = "END_PERIOD"
    }

    private lateinit var active: AndroidXMvpAppCompatFragment
    private val mainFragment = MainFragment()
    private val costsFragment = CostsFragment()
    private val chartFragment = ChartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = this.getSharedPreferences("PREFERENCE_STORAGE", Context.MODE_PRIVATE)
        openFragments()
        if (preferences.contains(FIRST_OPEN)) {
            return
        } else {
            val i = Intent(this, FirstActivity::class.java)
            startActivity(i)
            Log.d("qwerty", "first launch")
        }
    }

    private fun openFragments() {
        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.main_container, chartFragment, "chart").hide(chartFragment).commit()
        fm.beginTransaction().add(R.id.main_container, costsFragment, "costs").hide(costsFragment).commit()
        fm.beginTransaction().add(R.id.main_container, mainFragment, "main").commit()
        active = mainFragment
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_main -> {
                    fm.beginTransaction().hide(active).show(mainFragment).commit()
                    active = mainFragment
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_costs -> {
                    fm.beginTransaction().hide(active).show(costsFragment).commit()
                    active = costsFragment
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_chart -> {
                    fm.beginTransaction().hide(active).show(chartFragment).commit()
                    active = chartFragment
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}
