package com.example.holmi_production.money_counter_app.di

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */

val appComponent = listOf(
    databaseModule,
    daoModule,
    interactorModule,
    toolsModule,
    viewModelsModule,
    useCasesModule
)