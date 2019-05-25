package com.example.holmi_production.money_counter_app.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.holmi_production.money_counter_app.model.Spending

@Database(entities = [Spending::class],version = 1)
abstract class MoneyCounterDb :RoomDatabase(){
    abstract fun spendingDao():SpendingDao
}