package com.software.feature_products_impl.domain.interactors

import com.software.feature_products_impl.presentation.view_objects.ProductInListVO

interface ProductListUseCase {
    suspend fun getProducts(): List<ProductInListVO>?
    suspend fun addViewToProductInList(guid: String): ProductInListVO?
}