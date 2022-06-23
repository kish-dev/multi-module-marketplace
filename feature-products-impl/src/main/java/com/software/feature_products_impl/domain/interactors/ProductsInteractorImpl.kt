package com.software.feature_products_impl.domain.interactors

import com.software.core_utils.models.ServerResponse
import com.software.feature_products_impl.domain.mappers.mapToVO
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductsInteractorImpl (
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase {

    override suspend fun getProducts(): ServerResponse<List<ProductInListVO>> =
        withContext(dispatcher) {
            when(val products = productsRepository.getProducts()) {
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
            when(val products = productsRepository.addViewToProductInList(guid)) {
                is ServerResponse.Success -> {
                    ServerResponse.Success(products.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    ServerResponse.Error(products.throwable)
                }
            }
        }
}
