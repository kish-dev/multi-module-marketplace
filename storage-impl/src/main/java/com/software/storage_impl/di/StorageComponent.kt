package com.software.storage_impl.di

import android.content.Context
import com.software.storage_api.StorageApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface StorageComponent : StorageApi {

    companion object {

        @Volatile
        private var storageComponent: StorageComponent? = null

        fun initAndGet(appContext: Context): StorageComponent? =
            when (storageComponent) {
                null -> {
                    synchronized(this) {
                        when (storageComponent) {
                            null -> {
                                storageComponent = DaggerStorageComponent.builder()
                                    .appContext(appContext)
                                    .build()
                            }
                        }
                        storageComponent
                    }

                }
                else -> {
                    storageComponent
                }
            }

    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder
        fun build(): StorageComponent
    }
}
