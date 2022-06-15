package ru.ozon.route256.homework2.domain.repositories

import com.software.feature_api.models.ProductDTO
import com.software.feature_api.models.ProductInListDTO

interface ProductsRepository {
    suspend fun getProducts(): List<com.software.feature_api.models.ProductInListDTO>?

    suspend fun getProductById(guid: String): com.software.feature_api.models.ProductDTO?

    suspend fun addViewToProductInList(guid: String): com.software.feature_api.models.ProductInListDTO?

    suspend fun addProduct(productDTO: com.software.feature_api.models.ProductDTO): com.software.feature_api.models.ProductInListDTO?

}