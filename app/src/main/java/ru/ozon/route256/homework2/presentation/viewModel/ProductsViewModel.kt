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
            _lastChangedProduct.value = UiState.Loading()

            when(val product = interactor.addViewToProductInList(guid)) {
                null -> {
                    _lastChangedProduct.value = UiState.Error(NullPointerException())
                }
                else -> {
                    _lastChangedProduct.value = UiState.Success(product)
                }
            }
        }
    }
}