package com.software.feature_add_product_impl.di.modules

import com.software.feature_add_product_impl.data.AddProductRepositoryImpl
import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindRepository(repositoryImpl: AddProductRepositoryImpl): AddProductRepository
}