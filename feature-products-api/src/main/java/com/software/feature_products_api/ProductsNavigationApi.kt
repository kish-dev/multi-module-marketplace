package com.software.feature_products_api

import androidx.fragment.app.Fragment

interface ProductsNavigationApi {
    fun isClosed(fragment: Fragment): Boolean
    fun navigateToPDP(fragment: Fragment, guid: String)
    fun navigateToAddProduct(fragment: Fragment)
}