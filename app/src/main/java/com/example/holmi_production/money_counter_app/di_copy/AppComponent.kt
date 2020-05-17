package com.example.holmi_production.money_counter_app.di_copy

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */

val appComponent = listOf(
    databaseModule,
    repositoryModule,
    interactorModule,
    toolsModule,
    viewModelsModule
)