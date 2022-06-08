package ru.ozon.route256.homework2.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ozon.route256.homework2.domain.repositories.ProductsRepository
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO
import ru.ozon.route256.homework2.presentation.viewObject.utils.mappers.mapToVO

class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository,
    private val dispatcher: CoroutineDispatcher
) : ProductListUseCase,
    ProductDetailUseCase {
    override suspend fun getProducts(): List<ProductInListVO>? =
        withContext(dispatcher) {
            return@withContext productsRepository.getProducts()?.map { it.mapToVO() }
        }


    override suspend fun getProductById(guid: String): ProductVO? =
        withContext(dispatcher) {
            val product = productsRepository.getProductById(guid)
            product?.let {
                return@withContext product.mapToVO()
            }
            return@withContext null
        }


    override suspend fun addViewToProductInList(guid: String): ProductInListVO? =
        withContext(dispatcher) {
            return@withContext productsRepository.addViewToProductInList(guid)?.mapToVO()
        }


}

