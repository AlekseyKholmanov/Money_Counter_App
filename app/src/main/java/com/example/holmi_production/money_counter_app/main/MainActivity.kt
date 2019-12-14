package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import leakcanary.AppWatcher
import javax.inject.Inject

class MainActivity : AndroidXMvpAppCompatActivity() {

    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        val navController = findNavController(R.id.mainNavFragment)
        val graph = navController.graph
        if (!settingRepository.isOpened())
            graph.startDestination = R.id.navFirstLaunch
        else if (settingRepository.getIsEnd())
            graph.startDestination = R.id.navEndPeriod
        navController.graph = graph
        //WorkerManager.balancePopulateWork()
        WorkerManager.cancelAll()
        WorkerManager.startBalanceWorker()
        WorkerManager.startNotificationWorker()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this)
    }
}
