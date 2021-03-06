package com.software.feature_pdp_impl.presentation.view_models

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
import com.software.feature_pdp_impl.domain.interactors.ProductDetailUseCase
import kotlinx.coroutines.Dispatchers

class PDPViewModel(private val interactor: ProductDetailUseCase) : BaseViewModel() {

    private val _productLD: MutableLiveData<UiState<ProductVO>> = MutableLiveData(
        UiState.Init()
    )
    var productLD: LiveData<UiState<ProductVO>> =
        _productLD

    fun getProduct(productId: String) {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            _productLD.value = UiState.Loading()
            when (val product = interactor.getProductById(productId)) {
                is DomainWrapper.Success -> {
                    _productLD.value =
                        UiState.Success(product.value)
                }
                is DomainWrapper.Error -> {
                    _productLD.value =
                        UiState.Error(product.throwable)
                    _action.emit(Action.ShowToast(R.string.loading_error))
                }
            }
        }
    }

    fun changeCount(productId: String, countDiff: Int, count: Int = 0) {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            when (val product = interactor.changeCount(productId, countDiff, count)) {
                is DomainWrapper.Success -> {
                    _productLD.value =
                        UiState.Success(product.value)
                }
                is DomainWrapper.Error -> {
                    _productLD.value =
                        UiState.Error(product.throwable)
                    _action.emit(Action.ShowToast(R.string.update_cart_state_error))
                }
            }
        }
    }

    fun changeIsFavorite(guid: String, isFavorite: Boolean) {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            when (val product = interactor.changeIsFavorite(guid, isFavorite)) {
                is DomainWrapper.Success -> {
                    _productLD.value =
                        UiState.Success(product.value)
                }
                is DomainWrapper.Error -> {
                    _productLD.value =
                        UiState.Error(product.throwable)
                    _action.emit(Action.ShowToast(R.string.loading_error))
                }
            }
        }
    }
}