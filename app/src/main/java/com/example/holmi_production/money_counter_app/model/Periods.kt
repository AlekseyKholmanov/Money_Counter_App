package com.example.holmi_production.money_counter_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class Period(
    @PrimaryKey(autoGenerate = false)
    val id:String,

    val date:DateTime
)