package com.software.feature_pdp_impl.di.components

import com.software.feature_api.NetworkApi
import com.software.feature_pdp_api.PDPNavigationApi
import com.software.feature_pdp_impl.di.modules.PDPFeatureDependencies
import dagger.Component

@Component(dependencies = [NetworkApi::class, PDPNavigationApi::class])
interface PDPFeatureDependenciesComponent : PDPFeatureDependencies