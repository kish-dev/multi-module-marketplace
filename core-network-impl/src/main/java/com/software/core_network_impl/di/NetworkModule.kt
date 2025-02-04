package com.software.core_network_impl.di

import com.software.core_network_impl.call_adapters.ServerResponseCallAdapterFactory
import com.software.feature_api.BuildConfig
import com.software.feature_api.ProductsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsApi(okHttpClient: OkHttpClient): ProductsApi {
        return Retrofit.Builder()
            .baseUrl("https://675d888763b05ed079781f3e.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ServerResponseCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(ProductsApi::class.java)
    }
}