package com.software.feature_products_impl.di.components

import com.software.core_utils.di.PerFeature
import com.software.feature_api.NetworkApi
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.di.modules.ProductsFeatureDependencies
import com.software.storage_api.StorageApi
import com.software.worker_api.WorkerComponentInterface
import dagger.Component

@PerFeature
@Component(
    dependencies = [
        NetworkApi::class,
        ProductsNavigationApi::class,
        StorageApi::class,
        WorkerComponentInterface::class]
)
interface ProductsFeatureDependenciesComponent : ProductsFeatureDependencies