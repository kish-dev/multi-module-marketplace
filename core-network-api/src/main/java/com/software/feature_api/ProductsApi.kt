package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {
    @GET("/products")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("/products")
    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>
}