package ru.ozon.route256.homework2.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.ozon.route256.homework2.domain.repositories.ProductsRepository
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO
import ru.ozon.route256.homework2.presentation.viewObject.utils.mappers.mapToDTO
import ru.ozon.route256.homework2.presentation.viewObject.utils.mappers.mapToVO

class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase,
    ProductDetailUseCase,
    AddProductUseCase {
    override suspend fun getProducts(): List<ProductInListVO>? =
        withContext(dispatcher) {
            productsRepository.getProducts()?.map { it.mapToVO() }
        }


    override suspend fun getProductById(guid: String): ProductVO? =
        withContext(dispatcher) {
            val product = productsRepository.getProductById(guid)
            product?.let {
                product.mapToVO()
            }
            null
        }


    override suspend fun addViewToProductInList(guid: String): ProductInListVO? =
        withContext(dispatcher) {
            productsRepository.addViewToProductInList(guid)?.mapToVO()
        }

    override suspend fun addProductToAllPlaces(product: ProductVO) =
        withContext(dispatcher) {
            productsRepository.addProduct(product.mapToDTO())
        }
}

