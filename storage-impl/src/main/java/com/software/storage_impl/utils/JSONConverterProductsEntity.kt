package com.software.storage_impl.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.storage_impl.models.ProductEntity
import java.lang.reflect.Type

object JSONConverterProductsEntity {
    fun fromProductListEntity(productsEntities: List<ProductEntity>?): String? {
        if(productsEntities == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.toJson(productsEntities, type)
    }

    fun toProductListEntity(productsEntitiesString: String?): List<ProductEntity>? {
        if(productsEntitiesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.fromJson(productsEntitiesString, type)
    }
}