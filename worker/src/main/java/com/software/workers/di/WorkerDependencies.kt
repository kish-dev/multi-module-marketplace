package com.software.workers.di

import com.software.feature_api.ProductsApi
import com.software.storage_api.SharedPreferencesApi

interface WorkerDependencies {
    fun productsApi(): ProductsApi
    fun storageApi(): SharedPreferencesApi
}
