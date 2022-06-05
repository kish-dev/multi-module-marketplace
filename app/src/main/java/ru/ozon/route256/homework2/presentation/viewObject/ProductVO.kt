package ru.ozon.route256.homework2.presentation.viewObject

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

fun createProduct(
    name: String,
    description: String,
    image: String,
    price: String,
    rating: Double
): ProductVO {
    return ProductVO(
        guid = System.currentTimeMillis().toString(),
        name = name,
        price = price,
        description = description,
        rating = rating,
        isFavorite = false,
        isInCart = false,
        images = listOf(image),
        weight = null,
        count = null,
        availableCount = null,
        additionalParams = mapOf()
    )
}

