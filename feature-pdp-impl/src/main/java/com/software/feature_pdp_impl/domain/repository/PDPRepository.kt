package com.software.feature_pdp_impl.domain.repository

import com.software.core_utils.models.ProductDTO
import com.software.feature_api.wrappers.ServerResponse

interface PDPRepository {
    suspend fun getProductById(guid: String): ServerResponse<ProductDTO>
}