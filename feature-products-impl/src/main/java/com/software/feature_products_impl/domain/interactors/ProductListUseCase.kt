package com.software.feature_products_impl.domain.interactors

import com.software.core_utils.models.DomainWrapper
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem
import com.software.feature_products_impl.presentation.view_objects.DividedProductsInList

interface ProductListUseCase {
    suspend fun getProducts(): DomainWrapper<DividedProductsInList>
    suspend fun addViewToProductInList(guid: String): DomainWrapper<ProductsListItem.ProductInListVO>
    suspend fun updateProductCartState(
        guid: String,
        inCart: Boolean
    ): DomainWrapper<ProductsListItem.ProductInListVO>
}