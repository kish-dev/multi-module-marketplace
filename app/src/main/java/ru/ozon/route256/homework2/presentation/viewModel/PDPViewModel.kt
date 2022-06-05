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
import ru.ozon.route256.homework2.presentation.viewObject.UiState

class PDPViewModel(
    private val interactor: ProductDetailUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _productLD: MutableLiveData<UiState<ProductVO>> = MutableLiveData(UiState.Init())
    var productLD: LiveData<UiState<ProductVO>> = _productLD

    fun getProduct(productId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Loading()
            val product = withContext(dispatcher) {
                interactor.getProductById(productId)
            }
            when (product) {
                null -> {
                    _productLD.value = UiState.Error()
                }
                else -> {
                    _productLD.value = UiState.Success(product)
                }
            }
        }
    }
}