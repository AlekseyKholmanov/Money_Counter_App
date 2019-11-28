package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class SubCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val parentId: Int,
    val description: String,
    val isDeleted:Boolean = false
) : Parcelable