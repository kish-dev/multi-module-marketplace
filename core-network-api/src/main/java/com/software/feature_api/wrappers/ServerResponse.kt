package com.software.feature_api.wrappers

sealed class ServerResponse<T> {
    data class Error<T>(val throwable: Throwable) : ServerResponse<T>()
    data class Success<T>(val value: T) : ServerResponse<T>()
}
