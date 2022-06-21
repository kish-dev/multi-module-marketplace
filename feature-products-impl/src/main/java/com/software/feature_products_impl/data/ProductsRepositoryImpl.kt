package com.software.feature_products_impl.data

import android.content.Context
import com.software.feature_api.ProductsApi
import com.software.feature_api.models.ProductInListDTO
import com.software.feature_api.models.ServerResponse
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import javax.inject.Inject

//context for sharedPreferences and mapping data
class ProductsRepositoryImpl @Inject constructor(private val productsApi: ProductsApi, context: Context) :
    ProductsRepository {

    override suspend fun getProducts(): ServerResponse<List<ProductInListDTO>> =
        productsApi.getProductsInList()


    override suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO> =
        ServerResponse.Error(UnsupportedOperationException("String"))
//        productsApi.addViewToProductInList(guid)
}

