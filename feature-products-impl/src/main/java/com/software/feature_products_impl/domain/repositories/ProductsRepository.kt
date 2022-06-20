package com.software.feature_products_impl.domain.repositories

import com.software.feature_api.models.ProductInListDTO
import com.software.feature_api.models.ServerResponse

interface ProductsRepository {

    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>

    suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO>

}
