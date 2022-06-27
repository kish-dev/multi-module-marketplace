package com.software.feature_add_product_impl.domain.repositories

import com.software.core_utils.models.ProductDTO
import com.software.feature_api.wrappers.ServerResponse


interface AddProductRepository {

    suspend fun addProduct(productDTO: ProductDTO): ServerResponse<Boolean>
}