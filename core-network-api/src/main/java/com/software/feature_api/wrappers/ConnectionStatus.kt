package com.software.feature_api.wrappers

sealed class ConnectionStatus {
    /**
     * Internet error means that device connected to Internet which does not have a WAN connection
     * */
    object InternetError : ConnectionStatus()

    /**
     * Connection error means that device not connected to Internet
     * */
    object ConnectionError : ConnectionStatus()

    /**
     * Connected means that device connected to stable Internet
     * */
    object Connected : ConnectionStatus()
}