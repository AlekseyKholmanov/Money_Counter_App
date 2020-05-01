package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.ListItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpendingDetails(
    @Embedded
    var spending: SpendingEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    var category: CategoryEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    var subcategory: SubCategoryEntity?
):ListItem,
Parcelable
