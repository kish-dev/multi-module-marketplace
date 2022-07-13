package com.software.feature_products_impl.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.storage_api.SharedPreferencesApi
import com.software.workers.LoadAndSaveProductsInListWorker
import com.software.workers.LoadAndSaveProductsWorker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val sharedPreferencesApi: SharedPreferencesApi,
    private val context: Context
) : ProductsRepository {

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override suspend fun getProducts(): ServerResponse<List<ProductInListDTO>> =
        when (val cacheResponse = sharedPreferencesApi.getProductsInListDTO()) {
            null -> {
                ServerResponse.Error(RuntimeException("Cache is null after update"))
            }

            else -> {
                ServerResponse.Success(cacheResponse)
            }
        }


    override fun loadProducts(): Flow<WorkInfo> {
        val requestProducts = OneTimeWorkRequestBuilder<LoadAndSaveProductsWorker>().build()

        val requestProductsInList =
            OneTimeWorkRequestBuilder<LoadAndSaveProductsInListWorker>().build()

        workManager
            .beginWith(requestProducts)
            .then(requestProductsInList)
            .enqueue()

        return workManager.getWorkInfoByIdLiveData(requestProductsInList.id).asFlow()
    }

    override suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO> =
        when (val addedProductInListDTO = sharedPreferencesApi.addViewToProductInListDTO(guid)) {
            null -> {
                ServerResponse.Error(RuntimeException("Cache is null after added view count"))
            }

            else -> {
                ServerResponse.Success(addedProductInListDTO)
            }
        }

    override suspend fun updateProductCartState(
        guid: String,
        inCart: Boolean
    ): ServerResponse<ProductInListDTO> =
        when (val updatedProductInListDTO =
            sharedPreferencesApi.updateProductCartState(guid, inCart)) {
            null -> {
                ServerResponse.Error(RuntimeException("Cache is null after update"))
            }

            else -> {
                ServerResponse.Success(updatedProductInListDTO)
            }
        }

}
