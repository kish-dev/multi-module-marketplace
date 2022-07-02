package com.software.core_navigation_impl.di.components

import com.software.core_navigation_impl.di.NavigationDependencies
import com.software.feature_api.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [NetworkApi::class])
interface NavigationDependenciesComponent: NavigationDependencies {
}