package com.software.feature_products_impl.domain.repositories

import androidx.work.WorkInfo
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>

    suspend fun loadProducts(): Flow<WorkInfo>

    suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO>

}
