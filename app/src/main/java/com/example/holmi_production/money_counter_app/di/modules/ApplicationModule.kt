package com.example.holmi_production.money_counter_app.di.modules

import android.app.Application
import android.content.Context
import android.preference.Preference
import androidx.room.Room
import com.example.holmi_production.money_counter_app.orm.ExpenseDatabase
import dagger.Module
import dagger.Provides
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application){

    @Provides
    @Singleton
    fun provideApplicationContext(): Application = application

    private val DATABASE_NAME = "money_counter_db"
    @Provides
    @Singleton
    fun provideDatabase(context: Context): ExpenseDatabase {
        return Room.databaseBuilder( context, ExpenseDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}