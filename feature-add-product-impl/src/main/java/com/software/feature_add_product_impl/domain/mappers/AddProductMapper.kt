package com.software.feature_add_product_impl.domain.mappers

import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.feature_api.wrappers.ProductDTO

fun ProductVO.mapToDTO(): ProductDTO {
    return ProductDTO(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        images.firstOrNull() ?: "",
        weight,
        count,
        availableCount,
        additionalParams
    )
}

fun ProductDTO.mapToVO(): ProductVO {
    return ProductVO(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        listOf(images),
        weight,
        count,
        availableCount,
        additionalParams ?: emptyMap()
    )
}
