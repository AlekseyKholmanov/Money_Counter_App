package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Entity(tableName = "TransactionTable")
@Parcelize
data class TransactionEntity(
    @PrimaryKey(autoGenerate = false)
    val createdDate: DateTime,

    val sum: Double,

    val accountId: String,

    val categoryId: Int?,

    val subcategoryId:Int?,

    val comment:String?

) : Parcelable

