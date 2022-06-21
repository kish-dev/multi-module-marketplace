package com.software.feature_products_impl.di.modules

import android.content.Context
import com.software.feature_api.ProductsApi
import com.software.feature_products_impl.data.ProductsRepositoryImpl
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideRepository(
        productsApi: ProductsApi,
        context: Context
    ): ProductsRepository {
        return ProductsRepositoryImpl(productsApi, context)
    }
}