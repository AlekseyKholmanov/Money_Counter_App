package com.example.holmi_production.money_counter_app.model.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.ListItem

class SpendingWithCategory:ListItem {
    @Embedded
    lateinit var spending: Spending

    @Relation(parentColumn = "categoryId", entity = Category::class, entityColumn = "id")
    lateinit var category: List<Category>
}

class SpendingListItem(val spending: Spending, val category: Category?, val subCategory: SubCategory?):ListItem