package com.software.feature_products_impl.di.modules

import com.software.core_utils.di.PerFeature
import com.software.feature_products_impl.domain.interactors.LoadWithWorkersUseCase
import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.domain.interactors.ProductsInteractorImpl
import com.software.feature_products_impl.domain.repositories.ProductsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class InteractorModule {

    @Provides
    @PerFeature
    fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @PerFeature
    fun provideProductsInteractor(productsRepository: ProductsRepository, ): ProductListUseCase {
        return ProductsInteractorImpl(productsRepository, ioDispatcher())
    }

    @Provides
    @PerFeature
    fun provideLoadWithWorkersInteractor(productsRepository: ProductsRepository) : LoadWithWorkersUseCase {
        return ProductsInteractorImpl(productsRepository, ioDispatcher())
    }
}

