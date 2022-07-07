package com.software.feature_add_product_impl.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.software.core_utils.R
import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.common.Action
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.presentation.common.safeLaunch
import com.software.core_utils.presentation.view_models.BaseViewModel
import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.feature_add_product_impl.domain.interactors.AddProductUseCase
import kotlinx.coroutines.Dispatchers

class AddProductViewModel(
    private val interactor: AddProductUseCase,
) : BaseViewModel() {

    private val _addProductState: MutableLiveData<UiState<Boolean>> = MutableLiveData(
        UiState.Init()
    )
    var addProductState: LiveData<UiState<Boolean>> = _addProductState

    fun addProduct(productVO: ProductVO) {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            _addProductState.value = UiState.Loading()
            when (val productResult = interactor.addProductToAllPlaces(productVO)) {
                is DomainWrapper.Success -> {
                    _addProductState.value =
                        UiState.Success(productResult.value)
                    _action.send(Action.ShowToast(R.string.add_product_success))
                }
                is DomainWrapper.Error -> {
                    _addProductState.value =
                        UiState.Error(productResult.throwable)
                    _action.send(Action.ShowToast(R.string.loading_error))
                }
            }
        }
    }

}