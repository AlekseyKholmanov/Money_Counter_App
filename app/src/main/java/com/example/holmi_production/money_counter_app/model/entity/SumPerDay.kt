package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SumPerDay(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var sum: Double
) : Serializable {
    operator fun inc(inc: Double): SumPerDay {
        return SumPerDay(id, sum + inc)
    }
}