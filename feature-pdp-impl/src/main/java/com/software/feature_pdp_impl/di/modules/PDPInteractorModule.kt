package com.software.feature_pdp_impl.di.modules

import com.software.feature_pdp_impl.domain.interactors.PDPInteractorImpl
import com.software.feature_pdp_impl.domain.interactors.ProductDetailUseCase
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class PDPInteractorModule {

    @Provides
    fun provideInteractor(pdpRepository: PDPRepository): ProductDetailUseCase {
        return PDPInteractorImpl(pdpRepository, Dispatchers.IO)
    }
}