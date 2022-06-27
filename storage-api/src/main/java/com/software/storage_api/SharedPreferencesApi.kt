package com.software.storage_api

import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ProductInListDTO


interface SharedPreferencesApi {

    fun insertProductsDTO(productsDTO: List<ProductDTO>)

    fun insertProductsInListDTO(productsInListDTO: List<ProductInListDTO>)

    fun insertProductDTO(productDTO: ProductDTO): Boolean

    fun getProductsInListDTO(): List<ProductInListDTO>?

    fun getProductsDTO(): List<ProductDTO>?

    fun getProductById(guid: String): ProductDTO?

    fun addViewToProductInListDTO(guid: String): ProductInListDTO?

    fun insertNewProduct(productDTO: ProductDTO): Boolean

    fun insertProductInListDTO(productInListDTO: ProductInListDTO): Boolean
}
