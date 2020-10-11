package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime


@Entity(tableName = "CurrencyTable")
class CurrenciesEntity(
    @PrimaryKey
    val dateId: DateTime = DateTime.now().withTimeAtStartOfDay(),
    @ColumnInfo(name = "USD")
    val usd: Float,
    @ColumnInfo(name = "RUB")
    val ruble: Float,
    @ColumnInfo(name = "EUR")
    val euro: Float,
    @ColumnInfo(name = "ILS")
    val shakel: Float
)