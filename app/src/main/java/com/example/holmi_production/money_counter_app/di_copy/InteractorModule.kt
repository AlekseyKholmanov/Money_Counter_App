package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.interactor.BalanceInteractor
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.NotificationInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
val interactorModule = module {


    single{
        BalanceInteractor(get(), get(), get())
    }
    single{
        CategoryInteractor(get(), get())
    }
    single{
        NotificationInteractor(get(), get())
    }
    single{
        SpendingInteractor(get(), get(), get(), get(), get())
    }
}