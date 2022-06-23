package com.software.feature_add_product_impl.data

import com.software.feature_add_product_impl.domain.repositories.AddProductRepository
import com.software.feature_api.ProductsApi
import com.software.core_utils.models.ProductDTO
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(private val productsApi: ProductsApi) :
    AddProductRepository {

    override suspend fun addProduct(productDTO: ProductDTO): Boolean? =
//        productsApi.addProduct(productDTO)
        true
}