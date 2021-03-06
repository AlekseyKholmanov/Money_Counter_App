package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "SumPerDayTable")
data class SumPerDayEntity(

    @PrimaryKey
    val id: String,

    var sum: Double
) : Serializable {

    operator fun inc(inc: Double): SumPerDayEntity {
        return SumPerDayEntity(id, sum + inc)

    }
}