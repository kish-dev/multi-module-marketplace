package com.software.feature_products_impl.domain.interactors

import android.content.Context
import com.software.core_utils.models.DomainWrapper
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem

interface ProductListUseCase {
    suspend fun getProducts(): DomainWrapper<List<ProductsListItem.ProductInListVO>>
    suspend fun addViewToProductInList(guid: String): DomainWrapper<ProductsListItem.ProductInListVO>
    suspend fun updateProductCartState(
        guid: String,
        inCart: Boolean
    ): DomainWrapper<ProductsListItem.ProductInListVO>
    fun createProductsList(
        list: List<ProductsListItem.ProductInListVO>,
        context: Context,
        estimatedPrice: Int = 100,
    ): List<ProductsListItem>?
}