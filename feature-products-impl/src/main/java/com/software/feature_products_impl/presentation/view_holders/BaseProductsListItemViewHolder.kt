package com.software.feature_products_impl.presentation.view_holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem

abstract class BaseProductsListItemViewHolder<T : ProductsListItem>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    @Suppress("UNCHECKED_CAST")
    fun initBind(item: ProductsListItem) {
        (item as? T)?.let {
            bind(it)
        }
    }

    protected abstract fun bind(item: T)
}
