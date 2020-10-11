package com.example.holmi_production.money_counter_app.di

import com.example.holmi_production.money_counter_app.service.CurrencyService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L
private const val READ_TIMEOUT = 30L
private const val CALL_TIMEOUT = 60L
private const val BASE_URL = "https://api.exchangeratesapi.io/"

val networkModule = module {

    single(named("loginInterceptor")) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    single {
        OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(get(named("loginInterceptor")))
            .build()
    }

    single {
        GsonBuilder().create()
    }

    single {
        Retrofit
            .Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    ///region services

    single {
        get<Retrofit>().create(CurrencyService::class.java)
    }

    ///endregion

}