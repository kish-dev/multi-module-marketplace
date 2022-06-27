package com.software.feature_products_impl.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.common.UiState
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.domain.interactors.LoadWithWorkersUseCase
import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val interactor: ProductListUseCase,
    private val loadInteractor: LoadWithWorkersUseCase,
    private val router: ProductsNavigationApi
) : ViewModel() {

    companion object {
        private val TAG = ProductsViewModel::class.java.simpleName
    }

    private val _productLD: MutableLiveData<UiState<List<ProductInListVO>>> =
        MutableLiveData(UiState.Init())
    var productLD: LiveData<UiState<List<ProductInListVO>>> = _productLD

    private val _lastChangedProduct: MutableLiveData<UiState<ProductInListVO>> =
        MutableLiveData(UiState.Init())
    var lastChangedProduct: MutableLiveData<UiState<ProductInListVO>> = _lastChangedProduct

    fun getProducts() {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Loading()

            val responseFlow = loadInteractor.loadProducts()
            responseFlow.collect {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        Log.d(TAG, "WorkInfo.State.SUCCEEDED: ")
                        updateUiState()
                    }

                    WorkInfo.State.BLOCKED -> {
                        Log.d(TAG, "WorkInfo.State.BLOCKED: ")
                    }

                    WorkInfo.State.FAILED -> {
                        Log.d(TAG, "WorkInfo.State.FAILED: ")
                        _productLD.value = UiState.Error(Throwable("Something went wrong, FAILED"))
                    }

                    WorkInfo.State.CANCELLED -> {
                        Log.d(TAG, "WorkInfo.State.CANCELLED: ")
                        _productLD.value =
                            UiState.Error(Throwable("Something went wrong, CANCELLED"))
                    }

                    WorkInfo.State.ENQUEUED -> {
                        Log.d(TAG, "WorkInfo.State.ENQUEUED: ")
                    }

                    WorkInfo.State.RUNNING -> {
                        Log.d(TAG, "WorkInfo.State.RUNNING: ")
                    }
                }
            }
        }
    }

    private fun updateUiState() {
        viewModelScope.launch(Dispatchers.Main) {
            when (val products = interactor.getProducts()) {
                is DomainWrapper.Success -> {
                    _productLD.value =
                        UiState.Success(products.value)
                }
                is DomainWrapper.Error -> {
                    _productLD.value =
                        UiState.Error(products.throwable)
                }
            }
        }
    }

    fun addViewCount(guid: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _lastChangedProduct.value = UiState.Loading()
            when (val product = interactor.addViewToProductInList(guid)) {
                is DomainWrapper.Success -> {
                    _lastChangedProduct.value =
                        UiState.Success(product.value)
                }
                is DomainWrapper.Error -> {
                    _lastChangedProduct.value =
                        UiState.Error(product.throwable)
                }
            }
        }
    }
}
