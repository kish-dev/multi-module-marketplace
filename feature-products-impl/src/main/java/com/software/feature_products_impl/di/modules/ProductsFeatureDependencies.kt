package com.software.feature_products_impl.di.modules

import com.software.feature_api.ProductsApi
import com.software.feature_products_api.ProductsNavigationApi
import com.software.storage_api.StorageApi

interface ProductsFeatureDependencies {
    fun productsApi(): ProductsApi
    fun productNavigationApi(): ProductsNavigationApi
    fun storageApi(): StorageApi
}