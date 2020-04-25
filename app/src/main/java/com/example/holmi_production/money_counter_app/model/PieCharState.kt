package com.example.holmi_production.money_counter_app.model

import com.example.holmi_production.money_counter_app.model.entity.GraphEntity
import com.example.holmi_production.money_counter_app.model.entity.SpendingListItem

sealed class PieCharState {
    data class DetailsState(val spendings: Array<SpendingListItem>) : PieCharState()
    data class NormalState(val values: List<GraphEntity>, val canDetailed: Boolean) : PieCharState()
    object ErrorState: PieCharState()
}