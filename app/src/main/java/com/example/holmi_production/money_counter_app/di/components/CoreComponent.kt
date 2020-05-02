package com.example.holmi_production.money_counter_app.di.components

import android.content.Context
import com.example.holmi_production.money_counter_app.di.CoreTools
import com.example.holmi_production.money_counter_app.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
@Component(modules = [CoreModule::class, DatabaseModule::class])
@Singleton
interface CoreComponent: CoreTools {

    companion object {

        private lateinit var component: CoreComponent

        fun get(): CoreComponent = component

        fun init(
            context: Context
        ) {
            component = DaggerCoreComponent
                .builder()
                .context(context)
                .coreModule(CoreModule)
                .databaseModule(DatabaseModule)
                .build()
        }

    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun coreModule(coreModule: CoreModule): Builder

        fun databaseModule(databaseModule: DatabaseModule): Builder

        fun build(): CoreComponent

    }
}