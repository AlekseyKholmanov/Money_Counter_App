package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "BalanceTable")
data class BalanceEntity (
    @PrimaryKey(autoGenerate = false)
    val id: DateTime,
    val amount:Double)