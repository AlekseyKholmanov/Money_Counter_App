package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.interactor.SpendingInteractor
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.storage.SpendingDatabase
import com.example.holmi_production.money_counter_app.storage.SumPerDayDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @author Alexey Kholmanov (alexey.holmanov@cleverpumpkin.ru)
 */
class KeyboardViewModel(
    private val sumPerDayDatabase: SumPerDayDatabase,
    private val settingRepository: SettingRepository,
    private val spendingInteractor: SpendingInteractor,
    private val categoryInteractor: CategoryInteractor,
    private val spendingDatabase: SpendingDatabase
) : ViewModel() {

    private val _category = MutableLiveData<CategoryDetails?>()
    val categoryLiveData: LiveData<CategoryDetails?> = _category


    private val compositeDisposable = CompositeDisposable()

    fun observeCategory() {
        settingRepository.observeCategoryId()
            .asObservable()
            .subscribe {
                viewModelScope.launch {
                    _category.value = withContext(Dispatchers.IO) {
                        categoryInteractor.getCateforyDetails(it)
                    }
                }
            }
            .addTo(compositeDisposable)
    }

    fun saveSending(sum: Double, direction: SpDirection, comment: String, subCategoryId: Int?) {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}