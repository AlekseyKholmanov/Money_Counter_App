package com.example.holmi_production.money_counter_app.di_copy

import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import org.koin.dsl.module

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */

val daoModule = module {

    single { get<ExpenseDatabase>().balanceDao() }
    single { get<ExpenseDatabase>().categoryDao() }
    single { get<ExpenseDatabase>().periodsDao() }
    single { get<ExpenseDatabase>().transactionDao() }
    single { get<ExpenseDatabase>().subCategoryDao() }
    single { get<ExpenseDatabase>().sumPerDayDao() }
    single { get<ExpenseDatabase>().accountDao() }
    single { get<ExpenseDatabase>().recentAccountDao() }
    single { get<ExpenseDatabase>().recentCategoryDao() }
}