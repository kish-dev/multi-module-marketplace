package ru.ozon.route256.homework2.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ozon.route256.homework2.domain.interactors.ProductListUseCase
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO
import ru.ozon.route256.homework2.presentation.common.UiState

class ProductsViewModel(private val interactor: ProductListUseCase) : ViewModel() {

    private val _productLD: MutableLiveData<UiState<List<ProductInListVO>>> =
        MutableLiveData(UiState.Init())
    var productLD: LiveData<UiState<List<ProductInListVO>>> = _productLD

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Init()

            when (val products = interactor.getProducts()) {
                null -> {
                    _productLD.value = UiState.Error(NullPointerException())
                }
                else -> {
                    _productLD.value = UiState.Success(products)
                }
            }
        }
    }

    fun addViewCount(guid: String) {
        viewModelScope.launch(Dispatchers.Main) {
            interactor.addViewToProductInList(guid)
            getProducts()
        }
    }
}