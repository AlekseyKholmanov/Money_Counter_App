package com.example.holmi_production.money_counter_app.useCases

import com.example.holmi_production.money_counter_app.model.entity.AccountEntity

interface CreateAccountUseCase {

    suspend fun createAccount(account: AccountEntity)

}