package com.software.feature_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_api.wrappers.ServerResponse
import retrofit2.http.GET

interface ProductsApi {

    @GET("ee6876a1-8c02-45aa-bde4-b91817a8b210")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("d1b4763b-a5ea-471f-83bf-796da466e3d8")
    suspend fun getProducts(): ServerResponse<List<ProductDTO>>
}