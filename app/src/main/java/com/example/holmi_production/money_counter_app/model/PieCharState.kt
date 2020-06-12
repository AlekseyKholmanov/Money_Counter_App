package com.example.holmi_production.money_counter_app.model

sealed class PieCharState {

    data class DetailsState(val transactions: Array<TransactionDetails>) : PieCharState()

    data class NormalState(val values: List<GraphItem>, val canDetailed: Boolean) : PieCharState()

    object ErrorState: PieCharState()

}