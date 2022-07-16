package com.software.core_network_impl.di

import android.content.Context
import com.software.feature_api.NetworkApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ConnectionStatusModule::class])
abstract class CoreNetworkComponent : NetworkApi {

    companion object {

        @Volatile
        var networkComponent: CoreNetworkComponent? = null
            private set

        fun initAndGet(appContext: Context) =
            when (networkComponent) {
                null -> {
                    synchronized(this) {
                        when (networkComponent) {
                            null -> {
                                networkComponent = DaggerCoreNetworkComponent.builder()
                                    .appContext(appContext)
                                    .build()
                            }
                        }
                        networkComponent
                    }
                }

                else -> {
                    networkComponent
                }
            }

    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(appContext: Context): Builder
        fun build(): CoreNetworkComponent
    }
}
