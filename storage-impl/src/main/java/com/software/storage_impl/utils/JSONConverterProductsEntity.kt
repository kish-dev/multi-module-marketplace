package com.software.storage_impl.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.storage_impl.models.ProductEntity
import java.lang.reflect.Type

object JSONConverterProductsEntity {
    fun fromProductList(productsEntities: List<ProductEntity>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.toJson(productsEntities, type)
    }

    fun toProductList(productsEntitiesString: String): List<ProductEntity> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.fromJson(productsEntitiesString, type)
    }
}