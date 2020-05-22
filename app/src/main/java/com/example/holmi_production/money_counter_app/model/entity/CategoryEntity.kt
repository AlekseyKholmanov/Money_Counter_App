package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.SpDirection
import kotlinx.android.parcel.Parcelize


@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val spendingDirection: List<SpDirection>,
    override val description: String,
    override val color: Int,
    val imageId: Int? = null,
    var usageCount: Int = 0,
    override var isDeleted:Boolean = false
) : Nameble