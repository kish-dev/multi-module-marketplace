package com.software.feature_products_impl.presentation.view_objects

import com.software.feature_products_impl.presentation.adapters.ViewTypes

//abstract class BaseProductsTitleModel {
//    abstract val viewType: Int
//
//    override fun equals(other: Any?): Boolean {
//        return super.equals(other)
//    }
//
//    override fun hashCode(): Int {
//        return super.hashCode()
//    }
//}

sealed class BaseProductsTitleModel {

    abstract val viewType: Int


    data class TitleProductVO(
        val headerText: String,
        override val viewType: Int = ViewTypes.TITLE,
    ) : BaseProductsTitleModel()

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
    ) : BaseProductsTitleModel()

}