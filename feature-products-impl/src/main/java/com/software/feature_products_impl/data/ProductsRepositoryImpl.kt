package com.software.feature_products_impl.data

import android.content.Context
import com.software.core_utils.models.ProductInListDTO
import com.software.core_utils.models.ServerResponse
import com.software.feature_api.ProductsApi
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val context: Context
) :
    ProductsRepository {

    override suspend fun getProducts(): ServerResponse<List<ProductInListDTO>> {
//        val sharedPreferences = context.getDefaultSharedPreferences()
//        val cachedProductsInList = sharedPreferences.getString(Constants.PRODUCTS_IN_LIST_SP, null)
//        val
//        sharedPreferences.edit().putString("location", json).apply()
//        sharedPreferences.
        return productsApi.getProductsInList()
    }


    override suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO> =
        ServerResponse.Error(UnsupportedOperationException("String"))
//        productsApi.addViewToProductInList(guid)

}


