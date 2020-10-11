package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.CurrenciesEntity
import com.example.holmi_production.money_counter_app.service.CurrencyService
import com.example.holmi_production.money_counter_app.storage.db.CurrencyDatabase
import com.example.holmi_production.money_counter_app.useCases.FetchCurrencyUseCase

class FetchCurrencyUseCaseImpl(
    private val api: CurrencyService,
    val db: CurrencyDatabase
): FetchCurrencyUseCase {

    override suspend fun fetchAndSaveCurrencies(){
        val response = api.getCurrencies()
        response.body()?.let {
            db.save( CurrenciesEntity(
                usd = it.currencies.usd,
                euro = it.currencies.euro,
                shakel = it.currencies.sheckel,
                ruble = it.currencies.ruble
            ))
        }
    }

}