package com.software.workers.di

import com.software.feature_api.NetworkApi
import com.software.storage_api.StorageApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [NetworkApi::class, StorageApi::class])
interface WorkerDependenciesComponent : WorkerDependencies