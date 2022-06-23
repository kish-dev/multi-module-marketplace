package com.software.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.software.feature_api.ProductsApi
import com.software.core_utils.models.ServerResponse
import com.software.storage_api.SharedPreferencesApi
import javax.inject.Inject

class LoadAndSaveProductsWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {
        private val TAG = LoadAndSaveProductsWorker::class.java.simpleName
    }

    @Inject
    lateinit var productsApi: ProductsApi

    @Inject
    lateinit var sharedPreferencesApi: SharedPreferencesApi

    override fun doWork(): Result {

        return when(val response = productsApi.getProducts()) {
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

    override fun onStopped() {
        Log.d("RetrofitWorker", "onStopped!")
    }

}
