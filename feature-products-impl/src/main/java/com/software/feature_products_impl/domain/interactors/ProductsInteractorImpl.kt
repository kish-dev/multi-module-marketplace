package com.software.feature_products_impl.domain.interactors

import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import com.software.feature_products_impl.domain.mappers.mapToVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsInteractorImpl @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase {

    override suspend fun getProducts(): List<ProductInListVO>? =
        withContext(dispatcher) {
            productsRepository.getProducts()?.map { it.mapToVO() }
        }

    override suspend fun addViewToProductInList(guid: String): ProductInListVO? =
        withContext(dispatcher) {
            productsRepository.addViewToProductInList(guid)?.mapToVO()
        }
}
