package com.software.storage_impl.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.storage_impl.models.ProductInListEntity
import java.lang.reflect.Type

object JSONConverterProductsInListEntity {
    fun fromProductInListEntityList(productInListEntities: List<ProductInListEntity>?): String? {
        if (productInListEntities == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListEntity>>() {}.type
        return gson.toJson(productInListEntities, type)
    }

    fun toProductInListEntityList(productInListEntitiesString: String?): List<ProductInListEntity>? {
        if(productInListEntitiesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListEntity>>() {}.type
        return gson.fromJson(productInListEntitiesString, type)
    }
}