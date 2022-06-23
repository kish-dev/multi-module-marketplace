package com.software.feature_products_impl.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.models.ServerResponse
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel (
    private val interactor: ProductListUseCase,
    private val router: ProductsNavigationApi
    ) : ViewModel() {

    private val _productLD: MutableLiveData<UiState<List<ProductInListVO>>> =
        MutableLiveData(UiState.Init())
    var productLD: LiveData<UiState<List<ProductInListVO>>> = _productLD

    private val _lastChangedProduct: MutableLiveData<UiState<ProductInListVO>> =
        MutableLiveData(UiState.Init())
    var lastChangedProduct: MutableLiveData<UiState<ProductInListVO>> = _lastChangedProduct

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Loading()

            when (val products = interactor.getProducts()) {
                is ServerResponse.Success -> {
                    _productLD.value =
                        UiState.Success(products.value)
                }
                is ServerResponse.Error -> {
                    _productLD.value =
                        UiState.Error(products.throwable)
                }
            }
        }
    }

    fun addViewCount(guid: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _lastChangedProduct.value = UiState.Loading()
            when(val product = interactor.addViewToProductInList(guid)) {
                is ServerResponse.Success -> {
                    _lastChangedProduct.value =
                        UiState.Success(product.value)
                }
                is ServerResponse.Error -> {
                    _lastChangedProduct.value =
                        UiState.Error(product.throwable)
                }
            }
        }
    }
}
