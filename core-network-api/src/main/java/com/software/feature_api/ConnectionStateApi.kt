package com.software.feature_api

import com.software.feature_api.wrappers.ConnectionStatus
import kotlinx.coroutines.flow.StateFlow

interface ConnectionStateApi {

    fun getConnectionStatusFlow(): StateFlow<ConnectionStatus>
}