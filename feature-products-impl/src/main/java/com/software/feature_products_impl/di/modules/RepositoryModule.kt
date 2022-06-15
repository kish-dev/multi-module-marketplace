package com.software.feature_products_impl.di.modules

import com.software.core_utils.di.PerFeature
import com.software.feature_products_impl.data.ProductsRepositoryImpl
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @PerFeature
    fun bindRepository(repository: ProductsRepositoryImpl): ProductsRepository
}