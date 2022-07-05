package com.software.feature_products_impl.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.software.core_utils.presentation.adapters.ProductImageAdapter
import com.software.feature_products_impl.presentation.view_holders.BaseViewHolder
import com.software.feature_products_impl.presentation.view_holders.ProductViewHolder
import com.software.feature_products_impl.presentation.view_holders.ViewHolderFactory
import com.software.feature_products_impl.presentation.view_objects.BaseProductsTitleModel
import java.io.InvalidClassException
import java.lang.ClassCastException
import java.util.*

class ProductsAndTitlesAdapter(
    private val listener: Listener,
    private val viewHolderFactory: ViewHolderFactory,
    private val productImageAdapter: ProductImageAdapter
) : ListAdapter<BaseProductsTitleModel, BaseViewHolder<out BaseProductsTitleModel>>(
    BaseProductsTitleDiffUtil()
) {

    interface Listener {
        fun onClickProduct(holder: ProductViewHolder, productId: String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out BaseProductsTitleModel> {
        return viewHolderFactory.createViewHolder(
            parentView = parent,
            viewType = viewType,
            productImageAdapter = productImageAdapter,
            productListener = listener
        )
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<out BaseProductsTitleModel>,
        position: Int
    ) {
        holder.initBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is BaseProductsTitleModel.ProductInListVO -> {
                ViewTypes.PRODUCT_IN_LIST
            }
            is BaseProductsTitleModel.TitleProductVO -> {
                ViewTypes.TITLE
            }
        }


    private class BaseProductsTitleDiffUtil : DiffUtil.ItemCallback<BaseProductsTitleModel>() {
        override fun areItemsTheSame(
            oldItem: BaseProductsTitleModel,
            newItem: BaseProductsTitleModel
        ): Boolean =
            when {
                oldItem is BaseProductsTitleModel.ProductInListVO && newItem is BaseProductsTitleModel.ProductInListVO -> {
                    oldItem.guid == newItem.guid
                }

                oldItem is BaseProductsTitleModel.TitleProductVO && newItem is BaseProductsTitleModel.TitleProductVO -> {
                    oldItem.headerText == newItem.headerText
                }

                else -> {
                    throw ClassCastException("Unexpected class in BaseRVModelDiffUtil.areItemsTheSame")
                }
            }


        override fun areContentsTheSame(
            oldItem: BaseProductsTitleModel,
            newItem: BaseProductsTitleModel
        ): Boolean =
            when {
                oldItem is BaseProductsTitleModel.ProductInListVO && newItem is BaseProductsTitleModel.ProductInListVO -> {
                    oldItem == newItem
                }

                oldItem is BaseProductsTitleModel.TitleProductVO && newItem is BaseProductsTitleModel.TitleProductVO -> {
                    oldItem == newItem
                }

                else -> {
                    throw ClassCastException("Unexpected class in BaseRVModelDiffUtil.areItemsTheSame")
                }
            }

    }
}
