package com.software.storage_api

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO


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

    fun updateProductCartState(guid: String, inCart: Boolean): ProductInListDTO?

    fun changeProductCount(guid: String, countDiff: Int): ProductDTO?

    fun getLastSavedDraft(): ProductDTO?

    fun setDraft(productDTO: ProductDTO)

    fun clearDraft()

    fun changeIsFavorite(guid: String, isFavorite: Boolean): ProductDTO?
}
