package com.software.feature_add_product_impl.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.core_utils.presentation.common.UiState
import com.software.feature_add_product_impl.domain.interactors.AddProductUseCase
import com.software.feature_add_product_impl.presentation.view_objects.ProductVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val interactor: AddProductUseCase,
) : ViewModel() {

    private val _addProductState: MutableLiveData<UiState<Boolean>> = MutableLiveData(
        UiState.Init()
    )
    var addProductState: LiveData<UiState<Boolean>> = _addProductState

    fun addProduct(productVO: ProductVO) {
        viewModelScope.launch(Dispatchers.Main) {
            _addProductState.value = UiState.Loading()
            when (interactor.addProductToAllPlaces(productVO)) {
                true -> {
                    _addProductState.value = UiState.Success(true)
                }
                else -> {
                    _addProductState.value = UiState.Error(NullPointerException())
                }
            }
        }
    }

}