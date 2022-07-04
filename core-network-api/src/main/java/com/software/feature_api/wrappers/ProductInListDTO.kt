package com.software.feature_api.wrappers

data class ProductInListDTO(
    val guid: String,
    val image: List<String>,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    var viewsCount: Int = 0,
)





