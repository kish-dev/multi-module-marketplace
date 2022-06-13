package ru.ozon.route256.homework2.domain.interactors

import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO

interface ProductListUseCase {
    suspend fun getProducts(): List<ProductInListVO>?
    suspend fun addViewToProductInList(guid: String): ProductInListVO?
}