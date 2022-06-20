package com.software.feature_products_impl.di.components

import com.software.core_utils.di.PerFeature
import com.software.feature_api.NetworkApi
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.di.modules.ProductsFeatureDependencies
import dagger.Component

@PerFeature
@Component(dependencies = [NetworkApi::class, ProductsNavigationApi::class])
interface ProductsFeatureDependenciesComponent : ProductsFeatureDependencies