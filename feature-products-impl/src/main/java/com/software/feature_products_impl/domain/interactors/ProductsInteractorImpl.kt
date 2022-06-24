package com.software.feature_products_impl.domain.interactors

import androidx.work.WorkInfo
import com.software.core_utils.models.ProductInListDTO
import com.software.core_utils.models.ServerResponse
import com.software.feature_products_impl.domain.mappers.mapToVO
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase {

    override suspend fun getProducts(): ServerResponse<List<ProductInListVO>> =
        withContext(dispatcher) {
            when (val products = productsRepository.getProducts()) {
                is ServerResponse.Success -> {
                    ServerResponse.Success(products.value.map { it.mapToVO() })
                }

                is ServerResponse.Error -> {
                    ServerResponse.Error(products.throwable)
                }
            }
        }

    override suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListVO> =
        withContext(dispatcher) {
            when (val product = productsRepository.addViewToProductInList(guid)) {
                is ServerResponse.Success -> {
                    ServerResponse.Success(product.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    ServerResponse.Error(product.throwable)
                }
            }
        }

    override suspend fun loadProducts(): Flow<WorkInfo> =
        productsRepository.loadProducts()
}
