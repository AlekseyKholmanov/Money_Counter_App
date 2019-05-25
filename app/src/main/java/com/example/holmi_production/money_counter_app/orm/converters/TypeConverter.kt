package com.example.holmi_production.money_counter_app.orm.converters

import androidx.room.TypeConverter
import com.example.holmi_production.money_counter_app.model.CategoryTypes

class TypeConverter {
    @TypeConverter
    fun toType(type:Int): CategoryTypes {
        return CategoryTypes.values()[type]
    }

    @TypeConverter
    fun fromType(type:CategoryTypes): Int {
        return type.code
    }
}