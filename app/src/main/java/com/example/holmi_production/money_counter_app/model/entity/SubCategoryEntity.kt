package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class SubCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val parentId: Int,
    override val description: String,
    override val isDeleted: Boolean = false,
    override val color: Int
) : Nameble, Parcelable