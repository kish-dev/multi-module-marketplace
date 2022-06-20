package com.software.feature_products_impl.di.modules

import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.domain.interactors.ProductsInteractorImpl
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class InteractorModule {

    @Provides
    fun provideInteractor(productsRepository: ProductsRepository): ProductListUseCase {
        return ProductsInteractorImpl(productsRepository, Dispatchers.IO)
    }
}

