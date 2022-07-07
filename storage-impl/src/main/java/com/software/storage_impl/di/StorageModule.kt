package com.software.storage_impl.di

import android.content.Context
import com.google.gson.Gson
import com.software.storage_api.SharedPreferencesApi
import com.software.storage_impl.SharedPreferencesApiImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesImpl(appContext: Context)
            : SharedPreferencesApi {
        return SharedPreferencesApiImpl(appContext, Gson())
    }
}
