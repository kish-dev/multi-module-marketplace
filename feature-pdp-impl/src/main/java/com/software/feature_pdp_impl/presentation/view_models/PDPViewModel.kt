package com.software.feature_pdp_impl.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.core_utils.presentation.common.UiState
import com.software.feature_pdp_impl.domain.interactors.ProductDetailUseCase
import com.software.feature_pdp_impl.presentation.view_objects.ProductVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PDPViewModel (private val interactor: ProductDetailUseCase) : ViewModel() {

    private val _productLD: MutableLiveData<UiState<ProductVO>> = MutableLiveData(
        UiState.Init()
    )
    var productLD: LiveData<UiState<ProductVO>> =
        _productLD

    fun getProduct(productId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Loading()
            withContext(Dispatchers.Main) {
                when (val product = interactor.getProductById(productId)) {
                    null -> {
                        _productLD.value =
                            UiState.Error(
                                NullPointerException()
                            )
                    }
                    else -> {
                        _productLD.value =
                            UiState.Success(product)
                    }
                }
            }
        }
    }
}