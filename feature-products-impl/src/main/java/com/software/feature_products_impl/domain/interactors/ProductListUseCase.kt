package com.software.feature_products_impl.domain.interactors

import androidx.work.WorkInfo
import com.software.core_utils.models.ServerResponse
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import kotlinx.coroutines.flow.Flow

interface ProductListUseCase {
    suspend fun getProducts(): ServerResponse<List<ProductInListVO>>
    suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListVO>
    suspend fun loadProducts(): Flow<WorkInfo>
}