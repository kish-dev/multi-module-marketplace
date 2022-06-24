package com.software.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.software.core_utils.models.ServerResponse
import com.software.feature_api.ProductsApi
import com.software.storage_api.SharedPreferencesApi
import com.software.workers.utils.JSONConverterProductsDTO
import javax.inject.Inject

class LoadProductsWorker @Inject constructor(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val TAG = LoadProductsWorker::class.java.simpleName
    }

    @Inject
    lateinit var productsApi: ProductsApi

    @Inject
    lateinit var sharedPreferencesApi: SharedPreferencesApi

    override suspend fun doWork(): Result {

        return when (val response = productsApi.getProducts()) {
            is ServerResponse.Success -> {
                val outputData = Data.Builder()
                    .putString(
                        PRODUCTS_RESPONSE,
                        JSONConverterProductsDTO.fromProductListDTO(response.value)
                    ).build()
                Result.success(outputData)
            }
            is ServerResponse.Error -> {
                Log.d(TAG, "doWork: ${response.throwable} ${response.throwable.message}")
                Result.failure()
            }
        }
    }
}
