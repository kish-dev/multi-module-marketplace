package com.software.core_navigation_impl.di

import com.software.core_navigation_api.NavigationApi
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NavigationModule::class])
@Singleton
interface CoreNavigationComponent : NavigationApi