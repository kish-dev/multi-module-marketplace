package com.software.feature_add_product_impl.domain.interactors

import com.software.core_utils.models.ServerResponse
import com.software.feature_add_product_impl.presentation.view_objects.ProductVO

interface AddProductUseCase {
    suspend fun addProductToAllPlaces(product: ProductVO): ServerResponse<Boolean>?
}