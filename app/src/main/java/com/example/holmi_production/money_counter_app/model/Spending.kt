package com.example.holmi_production.money_counter_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.holmi_production.money_counter_app.orm.converters.DateConverter
import com.example.holmi_production.money_counter_app.orm.converters.TypeConverter
import java.util.*

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var price: Float,

    @TypeConverters(TypeConverter::class)
    var categoryTypes: CategoryTypes = CategoryTypes.NONE,

    @TypeConverters(DateConverter::class)
    var date: Date
)