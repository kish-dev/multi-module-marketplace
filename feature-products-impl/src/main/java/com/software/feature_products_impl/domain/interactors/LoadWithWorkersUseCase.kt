package com.software.feature_products_impl.domain.interactors

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface LoadWithWorkersUseCase {
    suspend fun loadProducts(): Flow<WorkInfo>
}