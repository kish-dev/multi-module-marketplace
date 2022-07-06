package com.software.feature_products_impl.presentation.view_objects

import android.content.Context
import com.software.core_utils.R

data class DividedProductsInList(
    var cheapProductsInList: List<BaseProductsTitleModel.ProductInListVO>,
    var expensiveProductsInList: List<BaseProductsTitleModel.ProductInListVO>
)

fun DividedProductsInList.mapToBaseRVModel(context: Context): List<BaseProductsTitleModel> {
    val resultList = mutableListOf<BaseProductsTitleModel>()
    when (this.cheapProductsInList.size) {
        0 -> {

        }

        else -> {
            resultList.add(BaseProductsTitleModel.TitleProductVO(headerText = context.getString(R.string.cheap_products)))
            resultList.addAll(this.cheapProductsInList)
        }
    }

    when (this.expensiveProductsInList.size) {
        0 -> {

        }

        else -> {
            resultList.add(BaseProductsTitleModel.TitleProductVO(headerText = context.getString(R.string.expensive_products)))
            resultList.addAll(this.expensiveProductsInList)
        }
    }

    return resultList
}
