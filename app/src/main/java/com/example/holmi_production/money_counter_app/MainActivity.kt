package com.example.holmi_production.money_counter_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.holmi_production.money_counter_app.chart.ChartFragment
import com.example.holmi_production.money_counter_app.costs.CostsFragment
import com.example.holmi_production.money_counter_app.main.MainFragment
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatFragment

class MainActivity : AppCompatActivity() {
    companion object {
        val FIRST_OPEN = "FirstOpen"
    }

    private lateinit var active: AndroidXMvpAppCompatFragment
    private val mainFragment = MainFragment()
    private val costsFragment = CostsFragment()
    private val chartFragment = ChartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.mainNavFragment)
//        val preferences = this.getSharedPreferences("PREFERENCE_STORAGE", Context.MODE_PRIVATE)
//        val navController =findNavController(R.id.mainNavFragment)
//
//        openFragments()
//        if (preferences.contains(FIRST_OPEN)) {
//            return
//        } else {
//            val i = Intent(this, FirstLaunchFragment::class.java)
//            startActivity(i)
//            Log.d("qwerty", "first launch")
//        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()

//    private fun openFragments() {
//        val fm = supportFragmentManager
//        fm.beginTransaction().add(R.id.main_container, chartFragment, "chart").hide(chartFragment).commit()
//        fm.beginTransaction().add(R.id.main_container, costsFragment, "costs").hide(costsFragment).commit()
//        fm.beginTransaction().add(R.id.main_container, mainFragment, "main").commit()
//        active = mainFragment
//        bottom_navigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.mainFragment -> {
//                    fm.beginTransaction().hide(active).show(mainFragment).commit()
//                    active = mainFragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.costsFragment -> {
//                    fm.beginTransaction().hide(active).show(costsFragment).commit()
//                    active = costsFragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.chartFragment -> {
//                    fm.beginTransaction().hide(active).show(chartFragment).commit()
//                    active = chartFragment
//                    return@setOnNavigationItemSelectedListener true
//                }
//            }
//            return@setOnNavigationItemSelectedListener false
//        }
//    }
}
