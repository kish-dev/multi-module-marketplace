package com.software.feature_products_impl.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.software.core_utils.presentation.common.ViewTypes
import com.software.feature_products_impl.presentation.view_holders.BaseProductsListItemViewHolder
import com.software.feature_products_impl.presentation.view_holders.ProductProductsListItemViewHolder
import com.software.feature_products_impl.presentation.view_holders.ViewHolderFactory
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem

class ProductsAndTitlesAdapter(
    private val listener: Listener,
    private val viewHolderFactory: ViewHolderFactory,
) : ListAdapter<ProductsListItem, BaseProductsListItemViewHolder<out ProductsListItem>>(
    BaseProductsTitleDiffUtil()
) {

    companion object {
        const val PAYLOAD_IS_IN_CART = "payload_is_in_cart"
        const val PAYLOAD_VIEWS_COUNT = "payload_views_count"
    }

    private val nestedRecyclerViewPool = RecyclerView.RecycledViewPool().apply {
        this.setMaxRecycledViews(ViewTypes.IMAGES, 3)
    }

    interface Listener {
        fun onClickProduct(holder: ProductProductsListItemViewHolder, productId: String)
        fun onChangeCartState(holder: ProductProductsListItemViewHolder, productId: String, inCart: Boolean)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseProductsListItemViewHolder<out ProductsListItem> {
        return viewHolderFactory.createViewHolder(
            parentView = parent,
            viewType = viewType,
            productListener = listener,
            recyclerViewPool = nestedRecyclerViewPool
        )
    }

    override fun onBindViewHolder(
        holder: BaseProductsListItemViewHolder<out ProductsListItem>,
        position: Int,
    ) {
        holder.initBind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseProductsListItemViewHolder<out ProductsListItem>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            when (holder) {
                is ProductProductsListItemViewHolder -> {
                    when (payloads[0]) {
                        PAYLOAD_IS_IN_CART -> {
                            (getItem(position) as? ProductsListItem.ProductInListVO)?.let {
                                holder.bindIsInCartState(
                                    it
                                )
                            }
                        }
                        PAYLOAD_VIEWS_COUNT -> {
                            (getItem(position) as? ProductsListItem.ProductInListVO)?.let {
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
            is ProductsListItem.ProductInListVO -> {
                ViewTypes.PRODUCT_IN_LIST
            }
            is ProductsListItem.TitleProductVO -> {
                ViewTypes.TITLE
            }
        }

    private class BaseProductsTitleDiffUtil : DiffUtil.ItemCallback<ProductsListItem>() {
        override fun areItemsTheSame(
            oldItem: ProductsListItem,
            newItem: ProductsListItem
        ): Boolean =
            when {
                oldItem is ProductsListItem.ProductInListVO && newItem is ProductsListItem.ProductInListVO -> {
                    oldItem.guid == newItem.guid
                }

                oldItem is ProductsListItem.TitleProductVO && newItem is ProductsListItem.TitleProductVO -> {
                    oldItem.headerText == newItem.headerText
                }

                else -> {
                    false
                }
            }


        override fun areContentsTheSame(
            oldItem: ProductsListItem,
            newItem: ProductsListItem
        ): Boolean =
            when {
                oldItem is ProductsListItem.ProductInListVO && newItem is ProductsListItem.ProductInListVO -> {
                    oldItem == newItem
                }

                oldItem is ProductsListItem.TitleProductVO && newItem is ProductsListItem.TitleProductVO -> {
                    oldItem == newItem
                }

                else -> {
                    false
                }
            }

        override fun getChangePayload(
            oldItem: ProductsListItem,
            newItem: ProductsListItem
        ): Any? {
            return when {
                oldItem is ProductsListItem.ProductInListVO && newItem is ProductsListItem.ProductInListVO -> {
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
