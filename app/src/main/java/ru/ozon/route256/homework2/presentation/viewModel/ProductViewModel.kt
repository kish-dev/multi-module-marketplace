package ru.ozon.route256.homework2.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ozon.route256.homework2.domain.interactors.ProductListUseCase
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO
import ru.ozon.route256.homework2.presentation.viewObject.UiState

class ProductViewModel(
    private val interactor: ProductListUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _productLD: MutableLiveData<UiState<List<ProductInListVO>>> =
        MutableLiveData(UiState.Init())
    var productLD: LiveData<UiState<List<ProductInListVO>>> = _productLD

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Init()
            val products = withContext(dispatcher) {
                interactor.getProducts()
            }
            when(products) {
                null -> {
                    _productLD.value = UiState.Error()
                }
                else -> {
                    _productLD.value = UiState.Success(products)
                }
            }
        }
    }

    fun addViewCount(guid: String) {
        viewModelScope.launch(dispatcher) {
            interactor.addViewToProductInList(guid)
            getProducts()
        }
    }
}