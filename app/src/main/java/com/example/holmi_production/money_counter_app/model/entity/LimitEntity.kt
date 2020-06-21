package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.util.*

@Entity(tableName = "LimitsTable")
data class LimitEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val categoryId:Int,

    val month: Int,

    val amount: Double,

    val startDate:DateTime,

    val endDate:DateTime

)