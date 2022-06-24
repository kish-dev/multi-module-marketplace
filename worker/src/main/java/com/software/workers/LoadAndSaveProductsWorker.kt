package com.software.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.software.core_utils.models.ServerResponse
import com.software.feature_api.NetworkApi
import com.software.storage_api.StorageApi
import com.software.workers.di.WorkerComponent
import javax.inject.Inject

class LoadAndSaveProductsWorker (
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val TAG = LoadAndSaveProductsWorker::class.java.simpleName
    }

    init {
        WorkerComponent.get()?.inject(this)
        Log.d(TAG, "productsApi: $networkApi")
        Log.d(TAG, "sharedPreferencesApi: $storageApi")
    }

    @Inject
    lateinit var networkApi: NetworkApi

    @Inject
    lateinit var storageApi: StorageApi

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: startWork")
        return when (val response = networkApi.getProductsApi().getProducts()) {
            is ServerResponse.Success -> {
                storageApi.getSharedPreferencesApi().insertProductsDTO(response.value)
                Result.success()
            }
            is ServerResponse.Error -> {
                Log.d(TAG, "doWork: ${response.throwable} ${response.throwable.message}")
                Result.failure()
            }
        }
    }
}
