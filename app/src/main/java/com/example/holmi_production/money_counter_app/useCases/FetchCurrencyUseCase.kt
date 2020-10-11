package com.example.holmi_production.money_counter_app.useCases

interface FetchCurrencyUseCase {

    suspend fun fetchAndSaveCurrencies()

}