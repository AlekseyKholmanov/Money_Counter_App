package com.example.holmi_production.money_counter_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.holmi_production.money_counter_app.extensions.EventObserver
import com.example.holmi_production.money_counter_app.extensions.checkAllMatched
import org.koin.androidx.viewmodel.ext.android.viewModel

class LauncherActivity : AppCompatActivity() {

    private val viewModel: LaunchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.launchDestination.observe(this, EventObserver { destination ->
            when (destination) {
                LaunchDestination.MAIN_ACTIVITY -> startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                LaunchDestination.ONBOARDING -> startActivity(
                    Intent(
                        this,
                        OnboardingActivity::class.java
                    )
                )
            }.checkAllMatched
            finish()
        })
    }

}