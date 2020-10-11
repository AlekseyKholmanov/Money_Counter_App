package com.example.holmi_production.money_counter_app.service

import com.google.gson.annotations.SerializedName

class GetCurrencyResponseBody(

    @SerializedName("rates")
    val currencies: Currencies,

    @SerializedName("date")
    val date: String

)

class Currencies(

    @SerializedName("USD")
    val usd: Float,
    @SerializedName("RUB")
    val ruble: Float,
    @SerializedName("EUR")
    val euro: Float,
    @SerializedName("ILS")
    val sheckel: Float
)
