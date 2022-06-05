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

class ProductViewModel(
    private val interactor: ProductListUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _productLD: MutableLiveData<List<ProductInListVO>> = MutableLiveData(emptyList())
    var productLD: LiveData<List<ProductInListVO>> = _productLD

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(dispatcher) {
            val products = interactor.getProducts()
            withContext(Dispatchers.Main) {
                _productLD.value = products
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