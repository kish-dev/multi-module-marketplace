package com.software.feature_api.wrappers

data class ProductDTO(
    val guid: String,
    val name: String,
    val price: String,
    val description: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val images: String,
    val weight: Double?,
    val count: Int?,
    val availableCount: Int?,
    val additionalParams: Map<String, String>?
)

fun ProductDTO.mapToProductInListDTO(): ProductInListDTO {
    return ProductInListDTO(
        guid = guid,
        image = images,
        name = name,
        price = price,
        rating = rating,
        isFavorite = isFavorite,
        isInCart = isInCart,
        viewsCount = 0,
    )
}

fun ProductInListDTO.mapToProductDTO(): ProductDTO {
    return ProductDTO(
        guid = guid,
        images = image,
        name = name,
        price = price,
        rating = rating,
        isFavorite = isFavorite,
        isInCart = isInCart,
        availableCount = 10,
        additionalParams = emptyMap(),
        weight = 10.0,
        description = "",
        count = 0,
    )
}
