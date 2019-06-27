package com.example.holmi_production.money_counter_app

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        val FIRST_OPEN = "FirstOpen"
    }

    @Inject lateinit var notificationManager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        notificationManager.setNotificationTime()
        val navController = findNavController(R.id.mainNavFragment)
        val graph = navController.graph
        val preferences = this.getSharedPreferences("PREFERENCE_STORAGE", Context.MODE_PRIVATE)
        if (!preferences.contains(FIRST_OPEN))
            graph.startDestination = R.id.navFirstLaunch
        navController.graph = graph

    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()

}
