package com.example.holmi_production.money_counter_app.orm

import androidx.room.TypeConverter
import com.example.holmi_production.money_counter_app.model.CategorySpendingDirection
import com.example.holmi_production.money_counter_app.model.CategoryType
import org.joda.time.DateTime

class Converters {
    @TypeConverter
    fun toType(type: Int): CategoryType {
        return CategoryType.values()[type]
    }

    @TypeConverter
    fun fromType(type: CategoryType): Int {
        return type.id
    }

    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long {
        return dateTime.millis
    }

    @TypeConverter
    fun dateToDateTime(date: Long): DateTime {
        return DateTime(date)
    }

    @TypeConverter
    fun fromCategoryDirection(direction: CategorySpendingDirection): Int {
        return direction.id
    }

    @TypeConverter
    fun toCategoryDirection(direction: Int): CategorySpendingDirection {
        return CategorySpendingDirection.values()[direction]
    }

}