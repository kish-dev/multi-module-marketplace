package com.software.core_navigation_impl.di

import com.software.core_network_impl.di.DaggerCoreNetworkComponent
import com.software.feature_add_product_impl.di.components.AddProductFeatureComponent
import com.software.feature_add_product_impl.di.components.DaggerAddProductFeatureDependenciesComponent
import com.software.feature_pdp_impl.di.components.DaggerPDPFeatureDependenciesComponent
import com.software.feature_pdp_impl.di.components.PDPFeatureComponent
import com.software.feature_products_impl.di.components.DaggerProductsFeatureDependenciesComponent
import com.software.feature_products_impl.di.components.ProductsFeatureComponent

object FeatureInjectorProxy {
    fun initFeatureProductsDI() {
        ProductsFeatureComponent.initAndGet(
            DaggerProductsFeatureDependenciesComponent.builder()
                .networkApi(DaggerCoreNetworkComponent.builder().build())
                .productsNavigationApi(DaggerCoreNavigationComponent.builder().build().getProductNavigation())
                .build()
        )
    }

    fun initFeaturePDPDI() {
        PDPFeatureComponent.initAndGet(
            DaggerPDPFeatureDependenciesComponent.builder()
                .networkApi(DaggerCoreNetworkComponent.builder().build())
                .pDPNavigationApi(DaggerCoreNavigationComponent.builder().build().getPDPNavigation())
                .build()
        )
    }

    fun initFeatureAddProductDI() {
        AddProductFeatureComponent.initAndGet(
            DaggerAddProductFeatureDependenciesComponent.builder()
                .networkApi(DaggerCoreNetworkComponent.builder().build())
                .addProductNavigationApi(DaggerCoreNavigationComponent.builder().build().getAddProductNavigation())
                .build()
        )
    }

}