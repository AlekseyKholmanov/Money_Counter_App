package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.ListItem
import kotlinx.android.parcel.Parcelize

class SpendingWithCategory:ListItem {
    @Embedded
    lateinit var spending: SpendingEntity

    @Relation(parentColumn = "categoryId", entity = CategoryEntity::class, entityColumn = "id")
    lateinit var category: List<CategoryEntity>
}


@Parcelize
class SpendingListItem(val spending: SpendingEntity, val category: CategoryEntity, val subCategory: SubCategoryEntity?):ListItem,
    Parcelable