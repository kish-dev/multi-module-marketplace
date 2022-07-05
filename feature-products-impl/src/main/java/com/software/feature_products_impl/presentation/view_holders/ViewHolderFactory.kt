package com.software.feature_products_impl.presentation.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.software.core_utils.presentation.adapters.ProductImageAdapter
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.adapters.ProductsAndTitlesAdapter
import com.software.feature_products_impl.presentation.adapters.ViewTypes
import com.software.feature_products_impl.presentation.view_objects.BaseProductsTitleModel
import java.io.InvalidClassException
import java.util.*

class ViewHolderFactory {

    fun createViewHolder(
        parentView: ViewGroup,
        viewType: Int,
        productImageAdapter: ProductImageAdapter?,
        productListener: ProductsAndTitlesAdapter.Listener?
    ): BaseViewHolder<out BaseProductsTitleModel> {
        val resultVH: BaseViewHolder<out BaseProductsTitleModel>

        when (viewType) {
            ViewTypes.PRODUCT_IN_LIST -> {

                when {
                    (productListener != null && productImageAdapter != null) -> {
                        resultVH = ProductViewHolder(
                            LayoutInflater.from(parentView.context)
                                .inflate(R.layout.product_list_item, parentView, false),
                            productListener,
                            ProductImageAdapter()
                        )
                    }

                    else -> {
                        throw InvalidPropertiesFormatException(
                            "in ViewHolderFactory.createViewHolder while creating ProductViewHolder and " +
                                    "productListener = $productListener productImageAdapter = $productImageAdapter"
                        )
                    }
                }

            }

            ViewTypes.TITLE -> {
                resultVH = TitleProductViewHolder(
                    LayoutInflater.from(parentView.context).inflate(R.layout.title_product_item, parentView, false)
                )
            }

            else -> {
                throw InvalidClassException("Invalid creating view holder")
            }
        }

        return resultVH
    }

}
