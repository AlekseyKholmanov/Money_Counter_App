package com.example.holmi_production.money_counter_app.di.components

import com.example.holmi_production.money_counter_app.App
import com.example.holmi_production.money_counter_app.di.AppDependencies
import com.example.holmi_production.money_counter_app.di.CoreTools
import com.example.holmi_production.money_counter_app.di.common.PerFeature
import com.example.holmi_production.money_counter_app.di.modules.InteractorModule
import com.example.holmi_production.money_counter_app.di.modules.RepositoryModule
import com.example.holmi_production.money_counter_app.di.modules.ViewModelModule
import com.example.holmi_production.money_counter_app.di.modules.WorkerModule
import com.example.holmi_production.money_counter_app.main.MainActivity
import com.example.holmi_production.money_counter_app.notification.NotificationAlarmReciever
import com.example.holmi_production.money_counter_app.ui.fragments.KeyboardPartFragment
import com.example.holmi_production.money_counter_app.ui.presenters.*
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartBalancePresenter
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartPiePresenter
import com.example.holmi_production.money_counter_app.ui.presenters.charts.ChartStackedPresenter
import dagger.Component


@Component(
    modules = [
        ViewModelModule::class,
        RepositoryModule::class,
        InteractorModule::class,
        WorkerModule::class],
    dependencies = [AppComponent.Dependencies::class]
)
@PerFeature
interface AppComponent {

    companion object {
        val instance: AppComponent by lazy(LazyThreadSafetyMode.PUBLICATION) {
            val appDependencies = DaggerAppComponent_Dependencies
                .builder()
                .coreTools(CoreComponent.get())
                .build()

            DaggerAppComponent
                .builder()
                .dependencies(appDependencies)
                .viewModelModule(ViewModelModule)
                .workerModule(WorkerModule)
                .repositoryModule(RepositoryModule)
                .interactorModule(InteractorModule)
                .build()
        }
    }

    fun getKeyboardPresenter(): KeyboardPresenter
    fun getCostsPresenter(): CostsPresenter
    fun getCreateCategoryPresenter(): CreateCategoryPresenter
    fun getSettingsPresenter(): SettingsPresenter
    fun getFirstLaunchPresenter(): FirstLaunchPresenter
    fun getChartPresenter(): ChartPiePresenter
    fun getEndPeriodPresenter(): EndPeriodPresenter
    fun getStackedPresenter(): ChartStackedPresenter
    fun getBalancePresenter(): ChartBalancePresenter
    fun getTopbarPresenter(): TopbarPresenter
    fun getCategoryPickerPresenter(): CategoryPickerPresenter
    fun getEditCategoryPresenter(): EditCategoryPresenter
    fun inject(app: App)
    fun inject(activity: MainActivity)
    fun inject(notificationAlarmReciever: NotificationAlarmReciever)
    fun inject(keyboardFr: KeyboardPartFragment)

    @Component(
        dependencies = [CoreTools::class]
    )
    interface Dependencies : AppDependencies
}
