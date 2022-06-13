package ru.ozon.route256.homework2.data.repositoriesImpl

import ru.ozon.route256.homework2.data.dto.ProductDTO
import ru.ozon.route256.homework2.data.dto.ProductInListDTO
import ru.ozon.route256.homework2.domain.repositories.ProductsRepository

class MockProductsRepositoryImpl : ProductsRepository {
    override suspend fun getProducts(): List<ProductInListDTO>? =
        productInListDTOs


    override suspend fun getProductById(guid: String): ProductDTO? {
        return productDTOs.firstOrNull { it.guid == guid }
    }

    override suspend fun addViewToProductInList(guid: String): ProductInListDTO? {
        val productInList = productInListDTOs.firstOrNull { it.guid == guid }
        productInList?.apply {
            ++viewsCount
        }
        return productInList
    }
}