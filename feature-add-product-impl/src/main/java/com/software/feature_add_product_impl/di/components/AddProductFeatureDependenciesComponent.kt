package com.software.feature_add_product_impl.di.components

import com.software.core_utils.di.PerFeature
import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_add_product_impl.di.modules.AddProductFeatureDependencies
import com.software.feature_api.NetworkApi
import com.software.storage_api.StorageApi
import dagger.Component

@PerFeature
@Component(dependencies = [NetworkApi::class, AddProductNavigationApi::class, StorageApi::class])
interface AddProductFeatureDependenciesComponent : AddProductFeatureDependencies