package com.example.holmi_production.money_counter_app.useCases.impl

import com.example.holmi_production.money_counter_app.model.entity.AccountEntity
import com.example.holmi_production.money_counter_app.storage.AccountDatabase
import com.example.holmi_production.money_counter_app.useCases.AddAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddAccountUseCaseImpl(
    private val accountDatabase: AccountDatabase
) : AddAccountUseCase {

    override suspend fun createAccount(account: AccountEntity) =
        withContext(Dispatchers.IO) {
            accountDatabase.insert(account)
        }


}