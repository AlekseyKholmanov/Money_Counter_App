package com.example.holmi_production.money_counter_app.main

import android.os.Bundle
import androidx.navigation.findNavController
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import javax.inject.Inject

class MainActivity : AndroidXMvpAppCompatActivity(){

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        notificationManager.setNotificationTime()
        val navController = findNavController(R.id.mainNavFragment)
        val graph = navController.graph
        if (!settingRepository.isOpened())
            graph.startDestination = R.id.navFirstLaunch
        else if (settingRepository.getIsEnd())
            graph.startDestination = R.id.navEndPeriod
        navController.graph = graph
    }
    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()
}
