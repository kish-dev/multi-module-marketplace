package ru.ozon.route256.homework2.presentation.viewObject

data class ProductInListVO(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    var viewsCount: Int,
)
