package com.software.feature_api

import com.software.feature_api.models.ProductDTO
import com.software.feature_api.models.ProductInListDTO

interface ProductsApi {

    suspend fun getProducts(): List<ProductInListDTO>?

    suspend fun getProductById(guid: String): ProductDTO?

    suspend fun addViewToProductInList(guid: String): ProductInListDTO?

    suspend fun addProduct(productDTO: ProductDTO): Boolean?
}