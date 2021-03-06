package com.software.core_utils.presentation.common

sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    class Error<T>(val throwable: Throwable) : UiState<T>()
    class Success<T>(val value: T) : UiState<T>()
    class Init<T> : UiState<T>()
}
