package com.software.feature_pdp_impl.domain.interactors

import com.software.core_utils.models.DomainWrapper
import com.software.feature_api.wrappers.ServerResponse
import com.software.core_utils.presentation.view_objects.ProductVO

interface ProductDetailUseCase {
    suspend fun getProductById(guid: String): DomainWrapper<ProductVO>
}