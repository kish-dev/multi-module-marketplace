package com.software.feature_pdp_impl.domain.interactors

import com.software.feature_api.models.ServerResponse
import com.software.feature_pdp_impl.presentation.view_objects.ProductVO

interface ProductDetailUseCase {
    suspend fun getProductById(guid: String): ServerResponse<ProductVO>
}