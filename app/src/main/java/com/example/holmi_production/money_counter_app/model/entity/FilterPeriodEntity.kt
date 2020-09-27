package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.extensions.withTimeAtEndOfDay
import com.example.holmi_production.money_counter_app.model.enums.PeriodType
import org.joda.time.DateTime

@Entity(tableName = "PeriodTable")
data class FilterPeriodEntity(
    @PrimaryKey(autoGenerate = false)

    val id: String = "DATE",

    val type: PeriodType = PeriodType.DAY,

    var from: DateTime = DateTime().withTimeAtStartOfDay(),

    var to: DateTime = DateTime().withTimeAtEndOfDay()

)