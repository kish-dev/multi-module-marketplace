package com.software.core_network_impl.data

import com.software.feature_api.models.ProductDTO
import com.software.feature_api.models.ProductInListDTO

fun ProductDTO.mapToProductListDTO(): ProductInListDTO {
    return ProductInListDTO(
        guid,
        images[0],
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        0
    )
}
