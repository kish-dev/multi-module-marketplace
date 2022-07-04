package com.software.feature_add_product_impl.domain.interactors

import com.software.core_utils.models.DomainWrapper
import com.software.core_utils.presentation.view_objects.ProductVO

interface AddProductUseCase {
    suspend fun addProductToAllPlaces(product: ProductVO): DomainWrapper<Boolean>?
}