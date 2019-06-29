package com.example.holmi_production.money_counter_app.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.R
import com.example.holmi_production.money_counter_app.mvp.AndroidXMvpAppCompatActivity
import com.example.holmi_production.money_counter_app.notification.NotificationManager
import javax.inject.Inject

class MainActivity : AndroidXMvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
        notificationManager.setNotificationTime()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mDayChangedReciever, IntentFilter("custom-intent-filter"))

        val navController = findNavController(R.id.mainNavFragment)
        val graph = navController.graph
        val preferences = this.getSharedPreferences("PREFERENCE_STORAGE", Context.MODE_PRIVATE)
        if (!preferences.contains(FIRST_OPEN))
            graph.startDestination = R.id.navFirstLaunch
        else if (preferences.getBoolean("IS_END",false))
            graph.startDestination = R.id.navEndPeriod
        navController.graph = graph

    }

    private val mDayChangedReciever: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.getNotification()
        }
    }

    @ProvidePresenter
    fun providePresenter() = App.component.getMainPresenter()

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavFragment).navigateUp()

    private val FIRST_OPEN = "FirstOpen"
}
