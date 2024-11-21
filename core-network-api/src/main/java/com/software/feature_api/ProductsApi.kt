package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {
    @GET("cdcb0ba4-d4f3-47a6-86ea-2f2a20c92030")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("cdcb0ba4-d4f3-47a6-86ea-2f2a20c92030")
    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>
}