package com.example.holmi_production.money_counter_app.di.modules

import androidx.work.ListenableWorker
import com.example.holmi_production.money_counter_app.worker.EndMonthTask
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
interface WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(EndMonthTask::class)
    fun bindEndTaskWorker(factory: EndMonthTask.Factory):ChildWorkerFactory
}

