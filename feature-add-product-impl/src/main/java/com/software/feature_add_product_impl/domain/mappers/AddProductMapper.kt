package com.software.feature_add_product_impl.domain.mappers

import com.software.feature_add_product_impl.presentation.view_objects.ProductVO
import com.software.feature_api.models.ProductDTO

fun ProductVO.mapToDTO(): ProductDTO {
    return ProductDTO(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        images,
        weight,
        count,
        availableCount,
        additionalParams
    )
}