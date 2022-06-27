package com.software.feature_products_impl.domain.mappers

import com.software.core_utils.models.ProductDTO
import com.software.core_utils.models.ProductInListDTO
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO

fun ProductInListDTO.mapToVO(): ProductInListVO {
    return ProductInListVO(
        guid,
        image,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        viewsCount
    )
}

fun ProductInListVO.mapToDTO(): ProductInListDTO {
    return ProductInListDTO(
        guid,
        image,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        viewsCount
    )
}

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
