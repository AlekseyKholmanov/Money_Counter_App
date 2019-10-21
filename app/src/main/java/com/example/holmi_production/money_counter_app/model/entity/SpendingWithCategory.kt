package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.ListItem

class SpendingWithCategory:ListItem {
    @Embedded
    lateinit var spending: Spending

    @Relation(parentColumn = "categoryType", entity = Category::class, entityColumn = "id")
    lateinit var category: List<Category>
}