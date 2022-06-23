package com.software.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.software.feature_api.ProductsApi
import com.software.core_utils.models.ServerResponse
import com.software.storage_api.SharedPreferencesApi
import com.software.workers.di.WorkerComponent
import javax.inject.Inject

class LoadAndSaveProductsInListWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    companion object {
        private val TAG = LoadAndSaveProductsInListWorker::class.java.simpleName
    }

    @Inject
    lateinit var productsApi: ProductsApi

    @Inject
    lateinit var sharedPreferencesApi: SharedPreferencesApi

    override fun doWork(): Result {
        WorkerComponent.workerComponent?.injectLoadAndSaveProductsInListWorker(this)
        return when (val response = productsApi.getProductsInList()) {
            is ServerResponse.Success -> {
                sharedPreferencesApi.insertProductsInListDTO(response.value)
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
