package com.software.core_utils.repository_models

data class RepositoryResponse<T, String>(
    private val value: T,
    private val error: String,
)