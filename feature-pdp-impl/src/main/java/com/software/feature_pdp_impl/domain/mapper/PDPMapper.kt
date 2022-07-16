package com.software.feature_pdp_impl.domain.mapper

import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.feature_api.wrappers.ProductDTO


fun ProductDTO.mapToVO(): ProductVO {
    return ProductVO(
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