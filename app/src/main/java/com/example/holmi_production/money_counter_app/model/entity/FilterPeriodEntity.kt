package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import org.joda.time.DateTime

@Entity
data class FilterPeriodEntity(
    @PrimaryKey(autoGenerate = false)

    val id: String,

    var leftBorder: DateTime = DateTime().withTimeAtStartOfDay(),

    var rightBorder: DateTime = DateTime().withTimeAtEndOfDay()

)