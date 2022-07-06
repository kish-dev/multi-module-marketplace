package com.software.feature_products_impl.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.common.ActionState
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.presentation.common.safeLaunch
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.domain.interactors.LoadWithWorkersUseCase
import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.presentation.view_objects.DividedProductsInList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class ProductsViewModel(
    private val interactor: ProductListUseCase,
    private val loadInteractor: LoadWithWorkersUseCase,
    private val router: ProductsNavigationApi
) : ViewModel() {

    companion object {
        private val TAG = ProductsViewModel::class.java.simpleName
    }

    private val _productRecyclerLD: MutableLiveData<UiState<DividedProductsInList>> =
        MutableLiveData(UiState.Init())
    var productRecyclerLD: LiveData<UiState<DividedProductsInList>> = _productRecyclerLD

    private val _action = Channel<ActionState<String>>()
    var action: Flow<ActionState<String>> =
        _action.receiveAsFlow()

    private val five_minutes = 300000L
    private var autoUpdateJob: Job? = null

    fun getProducts() {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            _productRecyclerLD.value = UiState.Loading()

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
                        val thx = Throwable("Something went wrong, FAILED")
                        _productRecyclerLD.value =
                            UiState.Error(thx)
                        _action.send(ActionState.Error(thx))
                    }

                    WorkInfo.State.CANCELLED -> {
                        Log.d(TAG, "WorkInfo.State.CANCELLED: ")
                        val thx = Throwable("Something went wrong, CANCELLED")
                        _productRecyclerLD.value =
                            UiState.Error(thx)
                        _action.send(ActionState.Error(thx))
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
        viewModelScope.safeLaunch(Dispatchers.Main) {
            when (val products = interactor.getProducts()) {
                is DomainWrapper.Success -> {
                    _productRecyclerLD.value =
                        UiState.Success(products.value)
                }
                is DomainWrapper.Error -> {
                    _productRecyclerLD.value =
                        UiState.Error(products.throwable)
                    _action.send(ActionState.Error(products.throwable))
                }
            }
        }
    }

    fun updateProductBucketState(guid: String, inCart: Boolean) {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            when (val product = interactor.updateProductBucketState(guid, inCart)) {
                is DomainWrapper.Success -> {
                    updateUiState()
                }
                is DomainWrapper.Error -> {
                    _action.send(ActionState.Error(product.throwable))
                }
            }
        }
    }

    fun addViewCount(guid: String) {
        viewModelScope.safeLaunch(Dispatchers.Main) {
            when (val product = interactor.addViewToProductInList(guid)) {
                is DomainWrapper.Success -> {
                    updateUiState()
                }
                is DomainWrapper.Error -> {
                    _action.send(ActionState.Error(product.throwable))
                }
            }
        }
    }

    fun autoUpdateProducts() {
        autoUpdateJob = viewModelScope.safeLaunch(Dispatchers.IO) {
            delay(five_minutes)
            getProducts()
            autoUpdateProducts()
        }
    }

    fun stopAutoUpdate() {
        autoUpdateJob?.cancel("Fragment invoke this method")
    }
}
