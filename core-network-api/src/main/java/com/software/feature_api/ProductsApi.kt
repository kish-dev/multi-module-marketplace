package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("9707da79-b769-4994-b2e9-6595a0557e74")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("4a6504f2-f809-4775-b74b-38f8b06b52a5")
    suspend fun getProducts(): ServerResponse<List<ProductDTO>>
}