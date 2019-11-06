package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.ListItem
import com.example.holmi_production.money_counter_app.model.SpDirection
import org.joda.time.DateTime

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = false)
    val createdDate: DateTime,

    val sum: Double,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int,

    val subcategoryId:Int?,

    val isSpending: SpDirection,

    val comment:String?

)

