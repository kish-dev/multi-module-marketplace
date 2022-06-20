package com.software.feature_pdp_impl.di.modules

import com.software.feature_pdp_impl.data.PDPRepositoryImpl
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindPDPRepository(repository: PDPRepositoryImpl): PDPRepository
}
