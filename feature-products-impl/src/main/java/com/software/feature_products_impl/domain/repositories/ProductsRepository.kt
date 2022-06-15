package com.software.feature_products_impl.domain.repositories

import com.software.feature_api.models.ProductInListDTO

interface ProductsRepository {

    suspend fun getProducts(): List<ProductInListDTO>?

    suspend fun addViewToProductInList(guid: String): ProductInListDTO?

}
