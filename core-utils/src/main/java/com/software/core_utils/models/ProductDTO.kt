package com.software.core_utils.models

data class ProductDTO(
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

fun ProductDTO.mapToProductInListDTO() :ProductInListDTO {
    return ProductInListDTO(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        viewsCount = 0)
}



