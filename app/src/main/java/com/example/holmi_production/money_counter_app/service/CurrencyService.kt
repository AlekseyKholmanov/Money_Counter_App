package com.example.holmi_production.money_counter_app.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("/latest")
    suspend fun getCurrencies(@Query("base") base: String = "USD"): Response<GetCurrencyResponseBody>

}