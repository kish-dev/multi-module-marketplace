package com.software.feature_api

interface NetworkApi {

    fun getProductsApi(): ProductsApi
    fun getConnectionStatusApi(): ConnectionStateApi
}