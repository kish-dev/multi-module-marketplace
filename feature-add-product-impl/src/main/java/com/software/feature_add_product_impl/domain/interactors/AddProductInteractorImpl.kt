package com.software.feature_add_product_impl.domain.interactors

import com.software.feature_add_product_impl.domain.mappers.mapToDTO
import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.feature_add_product_impl.presentation.view_objects.ProductVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddProductInteractorImpl(
    private val addProductRepository: AddProductRepository,
    private val dispatcher: CoroutineDispatcher
) : AddProductUseCase {

    override suspend fun addProductToAllPlaces(product: ProductVO) =
        withContext(dispatcher) {
            addProductRepository.addProduct(product.mapToDTO())
        }
}
