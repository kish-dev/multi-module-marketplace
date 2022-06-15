package ru.ozon.route256.homework2.domain.interactors

import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO

interface AddProductUseCase {
    suspend fun addProductToAllPlaces(product: ProductVO): ProductInListVO?
}