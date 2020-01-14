package com.example.holmi_production.money_counter_app.di.components
//
//import androidx.work.ListenableWorker
//import com.example.holmi_production.money_counter_app.di.modules.PreferenceModule
//import com.example.holmi_production.money_counter_app.worker.ChildWorkerFactory
//import com.example.holmi_production.money_counter_app.worker.EndMonthTask
//import com.example.holmi_production.money_counter_app.worker.SampleWorkerFactory
//import com.squareup.inject.assisted.dagger2.AssistedModule
//import dagger.Binds
//import dagger.Component
//import dagger.MapKey
//import dagger.Module
//import dagger.multibindings.IntoMap
//import kotlin.reflect.KClass
//
//@Component(
//    modules = [
//        SampleAssistedInjectModule::class,
//        WorkerBindingModule::class,
//        PreferenceModule::class
//    ]
//)
//interface DIComponent {
//    fun factory(): SampleWorkerFactory
//}
//
//@Module(includes = [AssistedInject_SampleAssistedInjectModule::class])
//@AssistedModule
//interface SampleAssistedInjectModule
//
//@MapKey
//@Target(AnnotationTarget.FUNCTION)
//@Retention(AnnotationRetention.RUNTIME)
//annotation class WorkerKey(val value: KClass<out ListenableWorker>)
//
//@Module
//interface WorkerBindingModule {
//    @Binds
//    @IntoMap
//    @WorkerKey(EndMonthTask::class)
//    fun bindEndMonthTask(factory: EndMonthTask.Factory): ChildWorkerFactory
//}