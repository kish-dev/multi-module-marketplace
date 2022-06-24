package com.software.workers.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.core_utils.models.ProductDTO
import java.lang.reflect.Type

object JSONConverterProductsDTO {
    fun fromProductListDTO(productsDTOs: List<ProductDTO>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductDTO>>() {}.type
        return gson.toJson(productsDTOs, type)
    }

    fun toProductListDTO(productsDTOsString: String): List<ProductDTO> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductDTO>>() {}.type
        return gson.fromJson(productsDTOsString, type)
    }
}