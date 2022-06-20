package com.software.feature_pdp_impl.domain.repository

import com.software.feature_api.models.ProductDTO
import com.software.feature_api.models.ServerResponse

interface PDPRepository {
    suspend fun getProductById(guid: String): ServerResponse<ProductDTO>
}