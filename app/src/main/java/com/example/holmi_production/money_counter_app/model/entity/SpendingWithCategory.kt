package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.ListItem
import kotlinx.android.parcel.Parcelize

class SpendingWithCategory:ListItem {
    @Embedded
    lateinit var spending: Spending

    @Relation(parentColumn = "categoryId", entity = Category::class, entityColumn = "id")
    lateinit var category: List<Category>
}


@Parcelize
class SpendingListItem(val spending: Spending, val category: Category, val subCategory: SubCategory?):ListItem,
    Parcelable