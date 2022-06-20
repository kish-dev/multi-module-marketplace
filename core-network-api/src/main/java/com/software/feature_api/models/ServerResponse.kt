package com.software.feature_api.models

import com.software.core_utils.presentation.common.UiState

sealed class ServerResponse<T> {
    data class Error<T>(val throwable: Throwable): ServerResponse<T>()
    data class Success<T>(val value: T): ServerResponse<T>()
}

fun ServerResponse<Any>.mapToUiState(): UiState<Any> =
    when(this) {
        is ServerResponse.Success -> {
            UiState.Success(value)
        }

        is ServerResponse.Error -> {
            UiState.Error(throwable)
        }
    }
