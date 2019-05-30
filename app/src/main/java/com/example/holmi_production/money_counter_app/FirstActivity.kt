package com.example.holmi_production.money_counter_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.first_launch_activity.*

class FirstActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_launch_activity)
        finish_activity.setOnClickListener {
            finish()
        }
    }

}