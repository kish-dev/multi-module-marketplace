package com.software.feature_add_product_impl.di.modules

import com.software.feature_add_product_impl.domain.interactors.AddProductInteractorImpl
import com.software.feature_add_product_impl.domain.interactors.AddProductUseCase
import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class AddProductInteractorModule {

    @Provides
    fun provideInteractor(pdpRepository: AddProductRepository): AddProductUseCase {
        return AddProductInteractorImpl(pdpRepository, Dispatchers.IO)
    }
}