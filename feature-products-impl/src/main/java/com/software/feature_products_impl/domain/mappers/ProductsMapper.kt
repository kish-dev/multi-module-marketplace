package com.software.feature_products_impl.domain.mappers

import com.software.feature_api.wrappers.ProductDTO
import com.software.feature_api.wrappers.ProductInListDTO
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem

fun ProductInListDTO.mapToVO(): ProductsListItem.ProductInListVO {
    return ProductsListItem.ProductInListVO(
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

fun ProductsListItem.ProductInListVO.mapToDTO(): ProductInListDTO {
    return ProductInListDTO(
        guid,
        images,
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
        images,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        0
    )
}
