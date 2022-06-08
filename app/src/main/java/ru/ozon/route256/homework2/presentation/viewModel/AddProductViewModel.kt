package ru.ozon.route256.homework2.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ozon.route256.homework2.domain.interactors.AddProductUseCase
import ru.ozon.route256.homework2.presentation.common.UiState
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO

class AddProductViewModel(
    private val interactor: AddProductUseCase,
) : ViewModel() {

    private val _addProductState: MutableLiveData<UiState<Boolean>> = MutableLiveData(UiState.Init())
    var addProductState: LiveData<UiState<Boolean>> = _addProductState

    fun addProduct(productVO: ProductVO) {
        viewModelScope.launch(Dispatchers.Main) {
            _addProductState.value = UiState.Loading()
            when (interactor.addProductToAllPlaces(productVO)) {
                true -> {
                    _addProductState.value = UiState.Success(true)
                }
                else -> {
                    _addProductState.value = UiState.Error(NullPointerException())
                }
            }
        }
    }

}