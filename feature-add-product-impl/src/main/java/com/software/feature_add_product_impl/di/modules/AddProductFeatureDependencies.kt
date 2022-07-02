package com.software.feature_add_product_impl.di.modules

import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_api.ProductsApi
import com.software.storage_api.StorageApi

interface AddProductFeatureDependencies {
    fun productsApi(): ProductsApi
    fun addProductNavigationApi(): AddProductNavigationApi
    fun storageApi(): StorageApi
}