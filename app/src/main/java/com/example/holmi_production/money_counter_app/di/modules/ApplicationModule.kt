package com.example.holmi_production.money_counter_app.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.holmi_production.money_counter_app.orm.MoneyCounterDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application){

    @Provides
    @Singleton
    fun provideApplicationContext(): Application = application

    private val DATABASE_NAME = "money_counter_db"
    @Provides
    @Singleton
    fun provideDatabase(context: Context): MoneyCounterDb {
        return Room.databaseBuilder( context, MoneyCounterDb::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}