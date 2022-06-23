package com.software.core_network_impl.di

import com.software.feature_api.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
abstract class CoreNetworkComponent : NetworkApi {

    companion object {

        @Volatile
        var networkComponent: CoreNetworkComponent? = null
            private set

        @Synchronized
        fun initAndGet() {
            when (networkComponent) {
                null -> {
                    networkComponent = DaggerCoreNetworkComponent.builder()
                        .build()
                }

                else -> {

                }
            }
        }
    }
}
