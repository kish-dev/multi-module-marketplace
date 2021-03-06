package com.software.core_utils.presentation.view_objects

import androidx.core.text.isDigitsOnly
import java.util.*

data class ProductVO(
    val guid: String,
    val name: String,
    val price: String,
    val description: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val images: List<String>,
    val weight: Double?,
    val count: Int?,
    val availableCount: Int?,
    val additionalParams: Map<String, String>
)

fun ProductVO.isNotBlank(): Boolean {
    return name.isNotBlank() || price.isNotBlank() || description.isNotBlank() || rating.compareTo(0.0) > 0 || images.isNotEmpty()
}

fun createProduct(
    name: String,
    description: String,
    images: List<String>,
    price: String,
    rating: Double,
    availableCount: String,
): ProductVO {

    val count = if(availableCount.isNotBlank() && availableCount.isDigitsOnly()) {
        availableCount.toInt()
    } else {
        0
    }

    return ProductVO(
        guid = UUID.randomUUID().toString(),
        name = name,
        price = price,
        description = description,
        rating = rating,
        isFavorite = false,
        isInCart = false,
        images = images,
        weight = null,
        count = null,
        availableCount = count,
        additionalParams = mapOf()
    )
}
