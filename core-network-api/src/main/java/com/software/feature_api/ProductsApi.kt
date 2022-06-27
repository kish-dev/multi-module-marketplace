package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("50afcd4b-d81e-473e-827c-1b6cae1ea1b2")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("8c374376-e94e-4c5f-aa30-a9eddb0d7d0a")
    suspend fun getProducts(): ServerResponse<List<ProductDTO>>
}