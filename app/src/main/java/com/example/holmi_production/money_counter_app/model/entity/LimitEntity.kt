package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class LimitEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId:Int,
    val month: Int,
    val amount: Double,
    val startDate:DateTime,
    val endDate:DateTime)