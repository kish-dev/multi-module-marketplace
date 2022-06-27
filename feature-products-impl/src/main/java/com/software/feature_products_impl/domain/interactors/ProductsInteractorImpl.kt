package com.software.feature_products_impl.domain.interactors

import androidx.work.WorkInfo
import com.software.core_utils.di.PerFeature
import com.software.core_utils.models.DomainWrapper
import com.software.feature_api.wrappers.ServerResponse
import com.software.feature_products_impl.domain.mappers.mapToVO
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@PerFeature
class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase, LoadWithWorkersUseCase {

    override suspend fun getProducts(): DomainWrapper<List<ProductInListVO>> =
        withContext(dispatcher) {
            when (val products = productsRepository.getProducts()) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(products.value.map { it.mapToVO() })
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(products.throwable)
                }
            }
        }

    override suspend fun addViewToProductInList(guid: String): DomainWrapper<ProductInListVO> =
        withContext(dispatcher) {
            when (val product = productsRepository.addViewToProductInList(guid)) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(product.value.mapToVO())
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(product.throwable)
                }
            }
        }

    override suspend fun loadProducts(): Flow<WorkInfo> =
        productsRepository.loadProducts()
}
