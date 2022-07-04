package com.software.feature_add_product_impl.domain.interactors

import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.feature_add_product_impl.domain.mappers.mapToDTO
import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.feature_api.wrappers.ServerResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddProductInteractorImpl(
    private val addProductRepository: AddProductRepository,
    private val dispatcher: CoroutineDispatcher
) : AddProductUseCase {

    override suspend fun addProductToAllPlaces(product: ProductVO) =
        withContext(dispatcher) {
            when (val response = addProductRepository.addProduct(product.mapToDTO())) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(response.value)
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(response.throwable)
                }
            }
        }
}
