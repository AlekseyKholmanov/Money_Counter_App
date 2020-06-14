package com.example.holmi_production.money_counter_app.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holmi_production.money_counter_app.interactor.CategoryInteractor
import com.example.holmi_production.money_counter_app.model.CategoryDetails
import com.example.holmi_production.money_counter_app.model.enums.SpDirection
import com.example.holmi_production.money_counter_app.storage.SettingRepository
import com.example.holmi_production.money_counter_app.useCases.GetRecentCategoryUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomKeyboardViewModel(
    private val getRecentCategoryUseCase: GetRecentCategoryUseCase
) : ViewModel() {

    private val _category = MutableLiveData<CategoryDetails?>()
    val category: LiveData<CategoryDetails?> = _category

    init {
        viewModelScope.launch {
            getRecentCategoryUseCase.observeResentCategory()
                .flowOn(Dispatchers.IO)
                .collect {
                    _category.value = it
                }
        }
    }

    fun saveSending(sum: Double, direction: SpDirection, comment: String, subCategoryId: Int?) {

    }
}