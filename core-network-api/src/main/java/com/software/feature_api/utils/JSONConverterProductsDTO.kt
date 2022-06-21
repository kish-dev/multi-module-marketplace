package com.software.feature_api.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.feature_api.models.ProductDTO
import java.lang.reflect.Type

object JSONConverterProductsDTO {
    fun fromProductList(questionsCacheEntities: List<ProductDTO>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductDTO>>() {}.type
        return gson.toJson(questionsCacheEntities, type)
    }

    fun toProductList(questionCacheEntitiesString: String): List<ProductDTO> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductDTO>>() {}.type
        return gson.fromJson(questionCacheEntitiesString, type)
    }
}