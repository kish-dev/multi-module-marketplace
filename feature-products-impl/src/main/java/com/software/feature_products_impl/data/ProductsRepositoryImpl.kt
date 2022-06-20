package com.software.feature_products_impl.data

import com.software.feature_api.ProductsApi
import com.software.feature_api.models.ProductInListDTO
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val productsApi: ProductsApi): ProductsRepository {

    override suspend fun getProducts(): List<ProductInListDTO>? =
        productsApi.getProducts()

    override suspend fun addViewToProductInList(guid: String): ProductInListDTO? =
        productsApi.addViewToProductInList(guid)
}