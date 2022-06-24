package com.software.core_utils.models

sealed class ServerResponse<T> {
    data class Error<T>(val throwable: Throwable) : ServerResponse<T>()
    data class Success<T>(val value: T) : ServerResponse<T>()
}
