package com.software.feature_pdp_impl.data

import com.software.feature_api.ProductsApi
import com.software.feature_api.models.ProductDTO
import com.software.feature_pdp_impl.domain.repository.PDPRepository
import javax.inject.Inject

class PDPRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi
): PDPRepository {
    override suspend fun getProductById(guid: String): ProductDTO? =
        productsApi.getProductById(guid)

}