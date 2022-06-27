package com.software.core_navigation_impl.di

import android.content.Context
import com.software.core_network_impl.di.CoreNetworkComponent
import com.software.feature_add_product_impl.di.components.AddProductFeatureComponent
import com.software.feature_add_product_impl.di.components.DaggerAddProductFeatureDependenciesComponent
import com.software.feature_pdp_impl.di.components.DaggerPDPFeatureDependenciesComponent
import com.software.feature_pdp_impl.di.components.PDPFeatureComponent
import com.software.feature_products_impl.di.components.DaggerProductsFeatureDependenciesComponent
import com.software.feature_products_impl.di.components.ProductsFeatureComponent
import com.software.storage_impl.di.StorageComponent
import com.software.workers.di.DaggerWorkerDependenciesComponent
import com.software.workers.di.WorkerComponent

object FeatureInjectorProxy {

    var isFirst = true

    fun initFeatureProductsDI(appContext: Context) {
        ProductsFeatureComponent.initAndGet(
            DaggerProductsFeatureDependenciesComponent.builder()
                .networkApi(CoreNetworkComponent.initAndGet(appContext))
                .productsNavigationApi(
                    DaggerCoreNavigationComponent.builder().build().getProductNavigation()
                )
                .storageApi(StorageComponent.initAndGet(appContext))
                .workerComponentInterface(
                    WorkerComponent.initAndGet(
                        DaggerWorkerDependenciesComponent.builder()
                            .networkApi(CoreNetworkComponent.initAndGet(appContext))
                            .storageApi(StorageComponent.initAndGet(appContext))
                            .build()
                    )
                )
                .build(),
            appContext
        )
    }

    fun initFeaturePDPDI(appContext: Context) {
        PDPFeatureComponent.initAndGet(
            DaggerPDPFeatureDependenciesComponent.builder()
                .networkApi(CoreNetworkComponent.initAndGet(appContext))
                .pDPNavigationApi(
                    DaggerCoreNavigationComponent.builder().build().getPDPNavigation()
                )
                .storageApi(StorageComponent.initAndGet(appContext))
                .build()
        )
    }

    fun initFeatureAddProductDI(appContext: Context) {
        AddProductFeatureComponent.initAndGet(
            DaggerAddProductFeatureDependenciesComponent.builder()
                .networkApi(CoreNetworkComponent.initAndGet(appContext))
                .addProductNavigationApi(
                    DaggerCoreNavigationComponent.builder().build().getAddProductNavigation()
                )
                .storageApi(StorageComponent.initAndGet(appContext))
                .build()
        )
    }

}