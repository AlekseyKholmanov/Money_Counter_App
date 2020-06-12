package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomKeyboardViewModel(
    private val settingRepository: SettingRepository,
    private val categoryInteractor: CategoryInteractor
) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()


    private val _category = MutableLiveData<CategoryDetails?>()


    val categoryLiveData: LiveData<CategoryDetails?> = _category
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