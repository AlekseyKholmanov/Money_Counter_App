package com.example.holmi_production.money_counter_app.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.holmi_production.money_counter_app.model.enums.CurrencyType
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.util.*

@Entity(
    tableName = "TransactionTable",
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId")
    ), ForeignKey(
        entity = SubCategoryEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("subcategoryId")
    )]
)
@Parcelize

data class TransactionEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val createdDate: DateTime,

    val sum: Double,

    val accountId: String,

    val categoryId: String? = null,

    val currencyType: CurrencyType,

    val subcategoryId: String? = null,

    val comment: String? = null,

    val isDeleted: Boolean = false

) : Parcelable

