package com.software.core_utils.models

sealed class DomainWrapper<T> {
    data class Error<T>(val throwable: Throwable) : DomainWrapper<T>()
    data class Success<T>(val value: T) : DomainWrapper<T>()
}
