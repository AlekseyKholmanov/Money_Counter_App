package com.example.holmi_production.money_counter_app.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.model.entity.CategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.SubCategoryEntity
import com.example.holmi_production.money_counter_app.model.entity.TransactionEntity


data class TransactionDetails(
    @Embedded
    var transaction: TransactionEntity,

    @Relation(
        parentColumn = "accountId",
        entityColumn = "id"
    )
    var accountEntity: AccountEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    var category: CategoryEntity?,

    @Relation(
        parentColumn = "subcategoryId",
        entityColumn = "id"
    )
    var subcategory: SubCategoryEntity?


)
