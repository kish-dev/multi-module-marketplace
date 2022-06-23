package com.software.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.software.core_utils.models.ServerResponse
import com.software.feature_api.ProductsApi
import com.software.storage_api.SharedPreferencesApi
import javax.inject.Inject

class LoadAndSaveProductsWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val TAG = LoadAndSaveProductsWorker::class.java.simpleName
    }

    @Inject
    lateinit var productsApi: ProductsApi

    @Inject
    lateinit var sharedPreferencesApi: SharedPreferencesApi

    override suspend fun doWork(): Result {

        return when (val response = productsApi.getProducts()) {
            is ServerResponse.Success -> {
                sharedPreferencesApi.insertProductsDTO(response.value)
                Result.success()
            }
            is ServerResponse.Error -> {
                Log.d(TAG, "doWork: ${response.throwable} ${response.throwable.message}")
                Result.failure()
            }
        }
    }
}
