package ru.ozon.route256.workshop1.sources

import ru.ozon.route256.workshop1.sources.ProductInListVO

data class ProductInListDTO(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean
) {
    fun toVO(): ProductInListVO {
        return ProductInListVO(guid, image, name, price, rating, isFavorite, isInCart)
    }
}
