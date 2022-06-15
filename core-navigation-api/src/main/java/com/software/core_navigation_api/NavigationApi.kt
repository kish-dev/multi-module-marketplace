package com.software.core_navigation_api

import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_pdp_api.PDPNavigationApi
import com.software.feature_products_api.ProductsNavigationApi

interface NavigationApi {
    fun getProductNavigation(): ProductsNavigationApi
    fun getPDPNavigation(): PDPNavigationApi
    fun getAddProductNavigation(): AddProductNavigationApi
}