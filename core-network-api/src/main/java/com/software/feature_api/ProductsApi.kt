package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("de9e8c2d-c4cb-458e-83a9-4ddfea568474")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("de9e8c2d-c4cb-458e-83a9-4ddfea568474")
    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>
}