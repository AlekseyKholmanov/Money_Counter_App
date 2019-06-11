package com.example.holmi_production.money_counter_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.io.Serializable

@Entity
data class SumPerDay(
    @PrimaryKey(autoGenerate = false)
    val dateTime: DateTime,

    var sum: Double
):Serializable