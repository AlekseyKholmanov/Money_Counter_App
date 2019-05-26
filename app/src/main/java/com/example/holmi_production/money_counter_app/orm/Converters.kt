package com.example.holmi_production.money_counter_app.orm

import androidx.room.TypeConverter
import com.example.holmi_production.money_counter_app.model.CategoryTypes
import org.joda.time.DateTime

class Converters {
    @TypeConverter
    fun toType(type:Int): CategoryTypes {
        return CategoryTypes.values()[type]
    }

    @TypeConverter
    fun fromType(type:CategoryTypes): Int {
        return type.code
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