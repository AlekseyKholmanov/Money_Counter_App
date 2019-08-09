package com.example.holmi_production.money_counter_app.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class FilterPeriods(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val leftBorder: DateTime,
    val rightBorder: DateTime
)