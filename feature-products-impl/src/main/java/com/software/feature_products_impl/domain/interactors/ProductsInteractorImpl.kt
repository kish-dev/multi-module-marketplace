package com.software.feature_products_impl.domain.interactors

import android.content.Context
import androidx.work.WorkInfo
import com.software.core_utils.R
import com.software.core_utils.di.PerFeature
import com.software.core_utils.models.DomainWrapper
import com.software.feature_api.wrappers.ServerResponse
import com.software.feature_products_impl.domain.mappers.mapToVO
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@PerFeature
class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase, LoadWithWorkersUseCase {

    override suspend fun getProducts(): DomainWrapper<List<ProductsListItem.ProductInListVO>> =
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

    override fun createProductsList(
        list: List<ProductsListItem.ProductInListVO>,
        context: Context,
        estimatedPrice: Int,
    ): List<ProductsListItem>? =
        try {
            val cheapList = list.filter { it.price.toInt() < estimatedPrice }
            val expensiveList = list.filter { it.price.toInt() >= estimatedPrice }
            val resultList = mutableListOf<ProductsListItem>()
            when (cheapList.size) {
                0 -> {

                }

                else -> {
                    resultList.add(ProductsListItem.TitleProductVO(headerText = context.getString(R.string.cheap_products)))
                    resultList.addAll(cheapList)
                }
            }

            when (expensiveList.size) {
                0 -> {

                }

                else -> {
                    resultList.add(ProductsListItem.TitleProductVO(headerText = context.getString(R.string.expensive_products)))
                    resultList.addAll(expensiveList)
                }
            }

            resultList
        } catch (e: NumberFormatException) {
            null
        }


    //TODO fix
    override suspend fun addViewToProductInList(guid: String): DomainWrapper<ProductsListItem.ProductInListVO> =
        withContext(dispatcher) {
            when (val product = productsRepository.getProducts()) {
                is ServerResponse.Success -> {
                    DomainWrapper.Success(product.value.first {it.guid == guid}.mapToVO())
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(product.throwable)
                }
            }
        }

    override suspend fun updateProductCartState(
        guid: String,
        inCart: Boolean
    ): DomainWrapper<ProductsListItem.ProductInListVO> =
        withContext(dispatcher) {
            when (val product = productsRepository.updateProductCartState(guid, inCart)) {
                is ServerResponse.Success -> {
                    when(product.value.isInCart == inCart) {
                        true -> DomainWrapper.Success(product.value.mapToVO())
                        false -> DomainWrapper.Error(Exception("Product cart state is not updated"))
                    }
                }

                is ServerResponse.Error -> {
                    DomainWrapper.Error(product.throwable)
                }
            }
        }


    override suspend fun loadProducts(): Flow<WorkInfo> =
        productsRepository.loadProducts()
}
