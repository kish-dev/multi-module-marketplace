package com.software.feature_api.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.feature_api.models.ProductInListDTO
import java.lang.reflect.Type

object JSONConverterProductsInListDTO {
    fun fromProductInListDTOList(questionsCacheEntities: List<ProductInListDTO>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListDTO>>() {}.type
        return gson.toJson(questionsCacheEntities, type)
    }

    fun toProductInListDTOList(questionCacheEntitiesString: String): List<ProductInListDTO> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListDTO>>() {}.type
        return gson.fromJson(questionCacheEntitiesString, type)
    }
}