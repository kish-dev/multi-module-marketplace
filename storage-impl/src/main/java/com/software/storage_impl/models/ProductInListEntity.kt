package com.software.storage_impl.models

data class ProductInListEntity(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    var viewsCount: Int,
)
