package com.software.feature_api

import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ProductInListDTO
import com.software.core_utils.models.ServerResponse
import retrofit2.http.GET

interface ProductsApi {

    //    suspend fun getProducts(): List<ProductInListDTO>?
//
//    suspend fun getProductById(guid: String): ProductDTO?
//
//    suspend fun addViewToProductInList(guid: String): ProductInListDTO?
//
//    suspend fun addProduct(productDTO: ProductDTO): Boolean?
    @GET("50afcd4b-d81e-473e-827c-1b6cae1ea1b2")
    suspend fun getProductsInList(): ServerResponse<List<ProductInListDTO>>

    @GET("8c374376-e94e-4c5f-aa30-a9eddb0d7d0a")
    suspend fun getProducts(): ServerResponse<List<ProductDTO>>
}