package com.example.holmi_production.money_counter_app.orm

import androidx.room.TypeConverter
import com.example.holmi_production.money_counter_app.model.SpDirection
import com.example.holmi_production.money_counter_app.model.CategoryType
import org.joda.time.DateTime

class Converters {

    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long {
        return dateTime.millis
    }

    @TypeConverter
    fun dateToDateTime(date: Long): DateTime {
        return DateTime(date)
    }

    @TypeConverter
    fun fromCategoryDirection(direction: SpDirection): Int {
        return direction.id
    }

    @TypeConverter
    fun toCategoryDirection(direction: Int): SpDirection {
        return SpDirection.values()[direction]
    }

    @TypeConverter
    fun fromListDirection(directions: List<SpDirection>): String {
        var string = ""
        directions.forEach {
            string += "${it.name} "
        }
        return string
    }

    @TypeConverter
    fun toListDirection(direction: String): List<SpDirection> {
        val list = direction.split(" ")
        return list.filter { it.isNotBlank() }
            .map { SpDirection.valueOf(it) }
    }

}