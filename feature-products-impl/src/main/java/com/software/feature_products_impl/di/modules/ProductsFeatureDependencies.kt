package com.software.feature_products_impl.di.modules

import com.software.feature_api.ProductsApi
import com.software.feature_products_api.ProductsNavigationApi

interface ProductsFeatureDependencies {
    fun productsApi(): ProductsApi
    fun productNavigationApi(): ProductsNavigationApi
}