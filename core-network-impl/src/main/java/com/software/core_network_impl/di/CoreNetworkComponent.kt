package com.software.core_network_impl.di

import com.software.feature_api.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface CoreNetworkComponent : NetworkApi