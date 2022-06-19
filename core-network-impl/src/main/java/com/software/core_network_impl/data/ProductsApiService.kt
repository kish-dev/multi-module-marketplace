package com.software.core_network_impl.data

import com.software.feature_api.models.ProductDTO
import com.software.feature_api.models.ProductInListDTO
import retrofit2.http.GET

interface ProductsApiService {

    @GET("v3/50afcd4b-d81e-473e-827c-1b6cae1ea1b2")
    suspend fun getProductInList(): Result<List<ProductInListDTO>>

    @GET("v3/8c374376-e94e-4c5f-aa30-a9eddb0d7d0a")
    suspend fun getProducts(): Result<List<ProductDTO>>
}