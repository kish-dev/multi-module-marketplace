package com.software.feature_add_product_impl.di.modules

import com.software.feature_add_product_impl.data.AddProductRepositoryImpl
import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.storage_api.StorageApi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providePDPRepository(storageApi: StorageApi): AddProductRepository {
        return AddProductRepositoryImpl(storageApi.getSharedPreferencesApi())
    }
}