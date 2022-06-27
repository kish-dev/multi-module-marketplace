package com.software.core_network_impl.data

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO

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
