package com.software.storage_api

import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ProductInListDTO


interface SharedPreferencesApi {

    fun insertProductsDTO(productsDTO: List<ProductDTO>)

    fun insertProductsInListDTO(productsInListDTO: List<ProductInListDTO>)

    fun insertProductInListDTO(productInListDTO: ProductInListDTO)

    fun insertProductDTO(productDTO: ProductDTO)

    fun getProductsInListDTO(): List<ProductInListDTO>?

    fun getProductById(guid: String): ProductDTO?
}
