package com.software.core_utils.presentation.common

sealed class ActionState<T> {
    class Success<T>(val value: T) : ActionState<T>()
    class Error<T>(val throwable: Throwable) : ActionState<T>()
}