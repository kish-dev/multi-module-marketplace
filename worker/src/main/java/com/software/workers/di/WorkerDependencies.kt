package com.software.workers.di

import com.software.feature_api.ProductsApi
import com.software.storage_api.SharedPreferencesApi
import com.software.storage_api.StorageApi

interface WorkerDependencies {
    fun productsApi(): ProductsApi
    fun storageApi(): SharedPreferencesApi
}
