package com.software.core_navigation_impl.di.components

import com.software.core_navigation_api.NavigationApi
import com.software.core_navigation_impl.MainActivity
import com.software.core_navigation_impl.di.NavigationDependencies
import com.software.core_navigation_impl.di.modules.NavigationModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [NavigationModule::class],
    dependencies = [NavigationDependencies::class]
)
@Singleton
abstract class CoreNavigationComponent : NavigationApi {

    companion object {

        @Volatile
        var coreNavigationComponent: CoreNavigationComponent? = null
            private set

        fun initAndGet(
            navigationDependencies: NavigationDependencies
        ): CoreNavigationComponent? =
            when (coreNavigationComponent) {
                null -> {
                    coreNavigationComponent = DaggerCoreNavigationComponent.builder()
                        .navigationDependencies(navigationDependencies)
                        .build()
                    coreNavigationComponent
                }

                else -> {
                    coreNavigationComponent
                }
            }
    }

    abstract fun inject(activity: MainActivity)
}