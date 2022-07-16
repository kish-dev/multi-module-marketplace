package com.software.feature_pdp_impl.domain.repository

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ServerResponse

interface PDPRepository {
    suspend fun getProductById(guid: String): ServerResponse<ProductDTO>
    suspend fun changeCount(guid: String, countDiff: Int): ServerResponse<ProductDTO>
    suspend fun changeIsFavorite(guid: String, isFavorite: Boolean): ServerResponse<ProductDTO>
}