package ru.ozon.route256.homework2.domain.interactors

import ru.ozon.route256.homework2.data.dto.mapToVO
import ru.ozon.route256.homework2.domain.repositories.ProductsRepository
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO

class ProductsInteractorImpl(private val productsRepository: ProductsRepository) :
    ProductListUseCase, ProductDetailUseCase {
    override suspend fun getProducts(): List<ProductInListVO> =
        productsRepository.getProducts().map { it.mapToVO() }

    override suspend fun getProductById(guid: String): ProductVO? {
        val product = productsRepository.getProductById(guid)
        product?.let {
            return product.mapToVO()
        }
        return null
    }

    override suspend fun addViewToProductInList(guid: String) {
        productsRepository.addViewToProductInList(guid)
    }


}