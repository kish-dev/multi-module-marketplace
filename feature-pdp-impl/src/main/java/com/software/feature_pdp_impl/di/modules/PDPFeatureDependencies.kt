package com.software.feature_pdp_impl.di.modules

import com.software.feature_api.ProductsApi
import com.software.feature_pdp_api.PDPNavigationApi
import dagger.Module

interface PDPFeatureDependencies {
    fun productsApi(): ProductsApi
    fun pdpNavigationApi(): PDPNavigationApi
}