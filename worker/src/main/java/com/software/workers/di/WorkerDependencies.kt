package com.software.workers.di

import com.software.feature_api.NetworkApi
import com.software.storage_api.StorageApi

interface WorkerDependencies {
    fun networkApi(): NetworkApi
    fun storageApi(): StorageApi
}
