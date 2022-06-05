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
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO
import ru.ozon.route256.homework2.presentation.viewObject.UiState

class AddProductViewModel(
    private val interactor: AddProductUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _addProductState: MutableLiveData<UiState<Boolean>> = MutableLiveData(UiState.Init())
    var addProductState: LiveData<UiState<Boolean>> = _addProductState

    fun addProduct(productVO: ProductVO) {
        viewModelScope.launch(Dispatchers.Main) {
            _addProductState.value = UiState.Loading()
            val isAdded = withContext(dispatcher) {
                interactor.addProductToAllPlaces(productVO)
            }
            when (isAdded) {
                true -> {
                    _addProductState.value = UiState.Success(true)
                }
                else -> {
                    _addProductState.value = UiState.Error()
                }
            }
        }
    }

}