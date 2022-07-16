package com.software.core_navigation_impl.di

import com.software.feature_api.ConnectionStateApi

interface NavigationDependencies {
    fun connectionStateApi(): ConnectionStateApi
}