package com.software.feature_products_impl.data

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.software.core_utils.Constants
import com.software.core_utils.presentation.common.getDefaultSharedPreferences
import com.software.feature_api.ProductsApi
import com.software.feature_api.models.ProductInListDTO
import com.software.feature_api.models.ServerResponse
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import javax.inject.Inject

//context for sharedPreferences and mapping data
class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val context: Context
) :
    ProductsRepository {

    override suspend fun getProducts(): ServerResponse<List<ProductInListDTO>> {
        val sharedPreferences = context.getDefaultSharedPreferences()
        val cachedProductsInList = sharedPreferences.getString(Constants.PRODUCTS_IN_LIST_SP, null)
        val
        sharedPreferences.edit().putString("location", json).apply()
        sharedPreferences.
        return productsApi.getProductsInList()

    }


    override suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO> =
        ServerResponse.Error(UnsupportedOperationException("String"))
//        productsApi.addViewToProductInList(guid)

    inner class Loader(context: Context, workerParameters: WorkerParameters) :
        Worker(context, workerParameters) {
        override fun doWork(): Result {
            val response = productsApi.getProducts()

            val outputData = Data.Builder()
                .putString("a", response)
                .build()

            return Result.success(outputData)

        }
    }

}


