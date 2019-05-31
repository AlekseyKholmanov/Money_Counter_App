package com.example.holmi_production.money_counter_app.orm

import androidx.room.TypeConverter
import com.example.holmi_production.money_counter_app.model.CategoryClass
import org.joda.time.DateTime

class Converters {
    @TypeConverter
    fun toType(type:Int): CategoryClass {
        return CategoryClass.values()[type]
    }

    @TypeConverter
    fun fromType(type: CategoryClass): Int {
        return type.ordinal
    }

    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long {
        return dateTime.millis
    }

    @TypeConverter
    fun dateToDateTime(date: Long): DateTime {
        return DateTime(date)
    }

}