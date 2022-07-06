package com.software.feature_products_impl.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.software.core_utils.presentation.adapters.ProductImageAdapter
import com.software.feature_products_impl.presentation.view_holders.BaseViewHolder
import com.software.feature_products_impl.presentation.view_holders.ProductViewHolder
import com.software.feature_products_impl.presentation.view_holders.ViewHolderFactory
import com.software.feature_products_impl.presentation.view_objects.BaseProductsTitleModel

class ProductsAndTitlesAdapter(
    private val listener: Listener,
    private val viewHolderFactory: ViewHolderFactory,
    private val productImageAdapter: ProductImageAdapter
) : ListAdapter<BaseProductsTitleModel, BaseViewHolder<out BaseProductsTitleModel>>(
    BaseProductsTitleDiffUtil()
) {

    companion object {
        const val PAYLOAD_IS_IN_CART = "payload_is_in_cart"
        const val PAYLOAD_VIEWS_COUNT = "payload_views_count"
    }

    interface Listener {
        fun onClickProduct(holder: ProductViewHolder, productId: String)
        fun onChangeCartState(holder: ProductViewHolder, productId: String, inCart: Boolean)
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
        position: Int,
    ) {
        holder.initBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<out BaseProductsTitleModel>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            when (holder) {
                is ProductViewHolder -> {
                    when (payloads[0]) {
                        PAYLOAD_IS_IN_CART -> {
                            (getItem(position) as? BaseProductsTitleModel.ProductInListVO)?.let {
                                holder.bindIsInCartState(
                                    it
                                )
                            }
                        }
                        PAYLOAD_VIEWS_COUNT -> {
                            (getItem(position) as? BaseProductsTitleModel.ProductInListVO)?.let {
                                holder.bindViewsCount(
                                    it
                                )
                            }
                        }
                        else -> {
                            onBindViewHolder(holder, position)
                        }
                    }
                }
                else -> {
                    onBindViewHolder(holder, position)
                }
            }
        }
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
                    false
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
                    false
                }
            }

        override fun getChangePayload(
            oldItem: BaseProductsTitleModel,
            newItem: BaseProductsTitleModel
        ): Any? {
            return when {
                oldItem is BaseProductsTitleModel.ProductInListVO && newItem is BaseProductsTitleModel.ProductInListVO -> {
                    var diff: MutableList<String>? = null
                    if(oldItem.guid == newItem.guid) {
                        diff = mutableListOf()
                        if (oldItem.isInCart != newItem.isInCart) {
                            diff.add(PAYLOAD_IS_IN_CART)
                        }
                        if (oldItem.viewsCount != newItem.viewsCount) {
                            diff.add(PAYLOAD_VIEWS_COUNT)
                        }
                    }
                    diff
                }

                else -> {
                    null
                }
            }
        }
    }

}
