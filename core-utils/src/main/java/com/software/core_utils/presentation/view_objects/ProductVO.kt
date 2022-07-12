package com.software.core_utils.presentation.view_objects

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
    image: String,
    price: String,
    rating: Double
): ProductVO {
    val images = when {
        image.isNotBlank() -> listOf(image)
        else -> listOf()
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
        availableCount = null,
        additionalParams = mapOf()
    )
}
