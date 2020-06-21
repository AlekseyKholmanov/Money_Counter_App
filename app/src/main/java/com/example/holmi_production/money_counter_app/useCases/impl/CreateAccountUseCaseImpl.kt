package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.storage.AccountDatabase
import com.example.holmi_production.money_counter_app.useCases.CreateAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateAccountUseCaseImpl(
    private val accountDatabase: AccountDatabase
) : CreateAccountUseCase {

    override suspend fun createAccount(account: AccountEntity) =
        withContext(Dispatchers.IO) {
            accountDatabase.insert(account)
        }


}