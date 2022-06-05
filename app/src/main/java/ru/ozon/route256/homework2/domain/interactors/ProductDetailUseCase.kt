package ru.ozon.route256.homework2.domain.interactors

import ru.ozon.route256.homework2.presentation.viewObject.ProductVO

interface ProductDetailUseCase {
    suspend fun getProductById(guid: String): ProductVO?
}