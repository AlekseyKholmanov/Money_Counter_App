package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.SpDirection
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val spendingDirection: List<SpDirection>,
    val description: String,
    val color: Int?,
    val imageId: Int? = null,
    var usageCount: Int = 0,
    var isDelete:Boolean = false
) : Parcelable
