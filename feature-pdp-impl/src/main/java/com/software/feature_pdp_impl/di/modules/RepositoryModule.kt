package com.software.feature_pdp_impl.di.modules

import com.software.feature_pdp_impl.data.PDPRepositoryImpl
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import com.software.storage_api.StorageApi
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providePDPRepository(storageApi: StorageApi): PDPRepository {
        return PDPRepositoryImpl(storageApi.getSharedPreferencesApi())
    }
}
