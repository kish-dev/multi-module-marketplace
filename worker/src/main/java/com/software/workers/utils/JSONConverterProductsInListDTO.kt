package com.software.workers.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.software.core_utils.models.ProductInListDTO
import java.lang.reflect.Type

object JSONConverterProductsInListDTO {
    fun fromProductInListDTOList(productsInListDTO: List<ProductInListDTO>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListDTO>>() {}.type
        return gson.toJson(productsInListDTO, type)
    }

    fun toProductInListDTOList(productsInListDTOString: String): List<ProductInListDTO> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ProductInListDTO>>() {}.type
        return gson.fromJson(productsInListDTOString, type)
    }
}