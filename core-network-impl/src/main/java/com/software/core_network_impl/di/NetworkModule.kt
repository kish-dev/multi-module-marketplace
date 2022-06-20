package com.software.core_network_impl.di

import com.software.core_network_impl.data.ProductsApiImpl
import com.software.feature_api.ProductsApi
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Binds
    fun bindProductsApi(api: ProductsApiImpl): ProductsApi
}