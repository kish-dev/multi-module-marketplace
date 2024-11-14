package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {
//https://run.mocky.io/v3/47d95b4a-2c03-45f2-bf21-9e277aec7691
    @GET("47d95b4a-2c03-45f2-bf21-9e277aec7691")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("47d95b4a-2c03-45f2-bf21-9e277aec7691")
    suspend fun getProducts(): ServerResponse<List<ProductInListDTO>>
}