package ru.ozon.route256.homework2.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ozon.route256.homework2.domain.interactors.ProductDetailUseCase
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO
import ru.ozon.route256.homework2.presentation.common.UiState

class PDPViewModel(private val interactor: ProductDetailUseCase) : ViewModel() {

    private val _productLD: MutableLiveData<UiState<ProductVO>> = MutableLiveData(UiState.Init())
    var productLD: LiveData<UiState<ProductVO>> = _productLD

    fun getProduct(productId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _productLD.value = UiState.Loading()
            withContext(Dispatchers.Main) {
                when (val product = interactor.getProductById(productId)) {
                    null -> {
                        _productLD.value = UiState.Error(NullPointerException())
                    }
                    else -> {
                        _productLD.value = UiState.Success(product)
                    }
                }
            }
        }
    }
}