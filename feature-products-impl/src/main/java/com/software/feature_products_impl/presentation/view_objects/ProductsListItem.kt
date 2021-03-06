package com.software.feature_products_impl.presentation.view_objects

import com.software.core_utils.presentation.common.ViewTypes

sealed class ProductsListItem {

    abstract val viewType: Int


    data class TitleProductVO(
        val headerText: String,
        override val viewType: Int = ViewTypes.TITLE,
    ) : ProductsListItem()

    data class ProductInListVO(
        val guid: String,
        val images: List<String>,
        val name: String,
        val price: String,
        val rating: Double,
        val isFavorite: Boolean,
        var isInCart: Boolean,
        var viewsCount: Int,
        override val viewType: Int = ViewTypes.PRODUCT_IN_LIST,
    ) : ProductsListItem()

}