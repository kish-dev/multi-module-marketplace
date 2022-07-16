package com.software.core_navigation_impl.di.modules

import com.software.core_navigation_impl.navigation.AddProductNavigationImpl
import com.software.core_navigation_impl.navigation.PDPNavigationImpl
import com.software.core_navigation_impl.navigation.ProductsNavigationImpl
import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_pdp_api.PDPNavigationApi
import com.software.feature_products_api.ProductsNavigationApi
import dagger.Binds
import dagger.Module

@Module
interface NavigationModule {

    @Binds
    fun bindProductNavigation(navigation: ProductsNavigationImpl): ProductsNavigationApi

    @Binds
    fun bindPDPNavigation(navigation: PDPNavigationImpl): PDPNavigationApi

    @Binds
    fun bindAddProductNavigation(navigation: AddProductNavigationImpl): AddProductNavigationApi

}