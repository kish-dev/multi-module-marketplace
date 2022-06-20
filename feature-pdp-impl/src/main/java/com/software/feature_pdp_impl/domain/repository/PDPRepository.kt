package com.software.feature_pdp_impl.domain.repository

import com.software.feature_api.models.ProductDTO

interface PDPRepository {
    suspend fun getProductById(guid: String): ProductDTO?
}