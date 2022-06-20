package com.software.feature_add_product_impl.domain.repositories

import com.software.feature_api.models.ProductDTO


interface AddProductRepository {

    suspend fun addProduct(productDTO: ProductDTO): Boolean?
}