package com.software.storage_impl.di

import android.content.Context
import android.content.SharedPreferences
import com.software.core_utils.presentation.common.getDefaultSharedPreferences
import com.software.storage_api.SharedPreferencesApi
import com.software.storage_impl.SharedPreferencesApiImpl
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun provideSharedPreferences(appContext: Context): SharedPreferences {
        return appContext.getDefaultSharedPreferences()
    }

    @Provides
    fun provideSharedPreferencesImpl(sharedPreferences: SharedPreferences)
            : SharedPreferencesApi {
        return SharedPreferencesApiImpl(sharedPreferences)
    }
}
