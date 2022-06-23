package com.software.storage_impl.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.storage_impl.models.ProductInListEntity
import java.lang.reflect.Type

object JSONConverterProductsInListEntity {
    fun fromProductInListDTOList(productInListEntities: List<ProductInListEntity>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListEntity>>() {}.type
        return gson.toJson(productInListEntities, type)
    }

    fun toProductInListDTOList(productInListEntitiesString: String): List<ProductInListEntity> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListEntity>>() {}.type
        return gson.fromJson(productInListEntitiesString, type)
    }
}