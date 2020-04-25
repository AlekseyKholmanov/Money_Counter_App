package com.example.holmi_production.money_counter_app.di.modules

import android.content.Context
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ExpenseDatabase {
        return ExpenseDatabase.getInstance(context)
    }
}