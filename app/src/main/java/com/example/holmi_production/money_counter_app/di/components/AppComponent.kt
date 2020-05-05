package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.di.AppDependencies
import com.example.holmi_production.money_counter_app.di.CoreTools
import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.di.modules.*
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever
import com.example.holmi_production.money_counter_app.ui.fragments.*
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartBalanceFragment
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartFragment
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartPieFragment
import com.example.holmi_production.money_counter_app.ui.fragments.charts.ChartStackedFragment
import dagger.Component


//@Component(
//    modules = [
//        ViewModelModule::class,
//        ViewModelFactoryModule::class,
//        RepositoryModule::class,
//        InteractorModule::class,
//        WorkerModule::class,
//        PresenterModule::class],
//    dependencies = [AppComponent.Dependencies::class]
//)
//@PerFeature
//interface AppComponent {
//
//    companion object {
//        val instance: AppComponent by lazy(LazyThreadSafetyMode.PUBLICATION) {
//            val appDependencies = DaggerAppComponent_Dependencies
//                .builder()
//                .coreTools(CoreComponent.get())
//                .build()
//
//            DaggerAppComponent
//                .builder()
//                .dependencies(appDependencies)
//                .build()
//        }
//    }
//
//    fun inject(app: App)
//    fun inject(fragment: SelectCategoryFragment)
//    fun inject(fragment: CostsFragment)
//    fun inject(fragment: CreateSubcategoryFragment)
//    fun inject(fragment: CreateCategoryFragment)
//    fun inject(fragment: EditCategoryFragment)
//    fun inject(fragment: EndPeriodFragment)
//    fun inject(fragment: KeyboardFragment)
//    fun inject(fragment: LimitsFragment)
//    fun inject(fragment: SettingsFragment)
//    fun inject(fragment: TopbarFragment)
//    fun inject(fragment: ChartFragment)
//    fun inject(fragment: ChartBalanceFragment)
//    fun inject(fragment: ChartPieFragment)
//    fun inject(fragment: ChartStackedFragment)
//    fun inject(fragment: CategoryDetailsFragment)
//    fun inject(activity: MainActivity)
//    fun inject(notificationAlarmReciever: NotificationAlarmReciever)
//
//    @Component(
//        dependencies = [CoreTools::class]
//    )
//    interface Dependencies : AppDependencies
//}
