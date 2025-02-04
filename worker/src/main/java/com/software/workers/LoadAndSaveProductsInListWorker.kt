package com.software.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.software.feature_api.NetworkApi
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import com.software.storage_api.StorageApi
import com.software.workers.di.WorkerComponent
import javax.inject.Inject

class LoadAndSaveProductsInListWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val TAG = LoadAndSaveProductsInListWorker::class.java.simpleName
    }

    init {

    }

    @Inject
    lateinit var networkApi: NetworkApi

    @Inject
    lateinit var storageApi: StorageApi

    override suspend fun doWork(): Result {
        WorkerComponent.get()?.inject(this)

        Log.d(TAG, "productsApi: $networkApi")
        Log.d(TAG, "sharedPreferencesApi: $storageApi")

        return when (val response = networkApi.getProductsApi().getProductsInList()) {
            is ServerResponse.Success -> {
                val newResponse: MutableSet<ProductInListDTO> = mutableSetOf()
                newResponse.add(response.value[0].copy(name = "null", price = "-199"))
                newResponse.addAll(response.value)

                val list = mutableListOf<ProductInListDTO>()
                list.addAll(newResponse)
                storageApi.getSharedPreferencesApi().insertProductsInListDTO(list)
                Result.success()
            }
            is ServerResponse.Error -> {
                Log.d(TAG, "doWork: ${response.throwable} ${response.throwable.message}")
                Result.failure()
            }
        }
    }
}
