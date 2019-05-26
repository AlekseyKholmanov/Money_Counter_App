package com.example.holmi_production.money_counter_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.holmi_production.money_counter_app.costs.ListItem
import com.example.holmi_production.money_counter_app.orm.Converters
import org.joda.time.DateTime
import java.io.Serializable

@Entity
data class Spending(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val price: Float,

    val categoryTypes: CategoryTypes,

    val spendingDate: DateTime
) : Serializable,ListItem