package ru.ozon.route256.homework2.domain.repositories

import ru.ozon.route256.homework2.data.dto.ProductDTO
import ru.ozon.route256.homework2.data.dto.ProductInListDTO

interface ProductsRepository {
    suspend fun getProducts(): List<ProductInListDTO>?

    suspend fun getProductById(guid: String): ProductDTO?

    suspend fun addViewToProductInList(guid: String)

}