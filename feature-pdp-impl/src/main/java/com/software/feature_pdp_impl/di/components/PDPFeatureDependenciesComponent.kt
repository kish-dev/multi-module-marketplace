package com.software.feature_pdp_impl.di.components

import com.software.core_utils.di.PerFeature
import com.software.feature_api.NetworkApi
import com.software.feature_pdp_api.PDPNavigationApi
import com.software.feature_pdp_impl.di.modules.PDPFeatureDependencies
import com.software.storage_api.StorageApi
import dagger.Component

@PerFeature
@Component(dependencies = [NetworkApi::class, PDPNavigationApi::class, StorageApi::class])
interface PDPFeatureDependenciesComponent : PDPFeatureDependencies