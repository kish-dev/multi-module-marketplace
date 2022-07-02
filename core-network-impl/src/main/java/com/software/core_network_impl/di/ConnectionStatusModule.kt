package com.software.core_network_impl.di

import com.software.core_network_impl.connection.ConnectionStateImpl
import com.software.feature_api.ConnectionStateApi
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ConnectionStatusModule {

    @Binds
    @Singleton
    fun bindConnectionStatusApi(connectionStateImpl: ConnectionStateImpl): ConnectionStateApi
}