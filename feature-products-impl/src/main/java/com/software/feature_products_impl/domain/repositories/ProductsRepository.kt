package com.software.feature_products_impl.domain.repositories

import com.software.core_utils.models.ProductInListDTO
import com.software.core_utils.models.ServerResponse

interface ProductsRepository {

    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>

    suspend fun addViewToProductInList(guid: String): ServerResponse<ProductInListDTO>

}
