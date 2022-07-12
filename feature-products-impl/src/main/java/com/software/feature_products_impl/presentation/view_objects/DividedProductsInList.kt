package com.software.feature_products_impl.presentation.view_objects

import android.content.Context
import com.software.core_utils.R

data class DividedProductsInList(
    var cheapProductsInList: List<ProductsListItem.ProductInListVO>,
    var expensiveProductsInList: List<ProductsListItem.ProductInListVO>
)

fun DividedProductsInList.mapToBaseRVModel(context: Context): List<ProductsListItem> {
    val resultList = mutableListOf<ProductsListItem>()
    when (this.cheapProductsInList.size) {
        0 -> {

        }

        else -> {
            resultList.add(ProductsListItem.TitleProductVO(headerText = context.getString(R.string.cheap_products)))
            resultList.addAll(this.cheapProductsInList)
        }
    }

    when (this.expensiveProductsInList.size) {
        0 -> {

        }

        else -> {
            resultList.add(ProductsListItem.TitleProductVO(headerText = context.getString(R.string.expensive_products)))
            resultList.addAll(this.expensiveProductsInList)
        }
    }

    return resultList
}
