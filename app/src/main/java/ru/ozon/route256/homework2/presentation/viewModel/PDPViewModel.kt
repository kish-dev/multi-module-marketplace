package ru.ozon.route256.homework2.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ozon.route256.homework2.domain.interactors.ProductDetailUseCase
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO

class PDPViewModel(
    private val interactor: ProductDetailUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _productLD: MutableLiveData<ProductVO?> = MutableLiveData(null)
    var productLD: LiveData<ProductVO?> = _productLD

    fun getProduct(productId: String) {
        viewModelScope.launch(dispatcher) {
            val product = interactor.getProductById(productId)
            withContext(Dispatchers.Main) {
                _productLD.value = product
            }
        }
    }
}