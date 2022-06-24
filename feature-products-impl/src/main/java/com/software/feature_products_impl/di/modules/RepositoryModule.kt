package com.software.feature_products_impl.di.modules

import android.content.Context
import com.software.feature_products_impl.data.ProductsRepositoryImpl
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import com.software.storage_api.StorageApi
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(
        storageApi: StorageApi,
        appContext: Context
    ): ProductsRepository {
        return ProductsRepositoryImpl(storageApi.getSharedPreferencesApi(), appContext)
    }
}