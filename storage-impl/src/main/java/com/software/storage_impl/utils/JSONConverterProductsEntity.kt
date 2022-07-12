package com.software.storage_impl.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.storage_impl.models.ProductEntity
import java.lang.reflect.Type

class JSONConverterProductsEntity(private val gson: Gson) {
    fun fromProductsListEntity(productsEntities: List<ProductEntity>?): String? {
        if (productsEntities == null) {
            return null
        }
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.toJson(productsEntities, type)
    }

    fun toProductsListEntity(productsEntitiesString: String?): List<ProductEntity>? {
        if (productsEntitiesString == null) {
            return null
        }
        val type: Type = object : TypeToken<List<ProductEntity>>() {}.type
        return gson.fromJson(productsEntitiesString, type)
    }

    fun fromProductEntity(productEntity: ProductEntity?): String? {
        if (productEntity == null) {
            return null
        }
        return gson.toJson(productEntity)
    }

    fun toProductEntity(productEntityString: String?): ProductEntity? {
        if (productEntityString == null) {
            return null
        }
        return gson.fromJson(productEntityString, ProductEntity::class.java)
    }
}