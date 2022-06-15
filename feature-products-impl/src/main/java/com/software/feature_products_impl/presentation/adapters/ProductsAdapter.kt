package com.software.feature_products_impl.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.view_holders.ProductViewHolder
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO

class ProductsAdapter(private val listener: Listener) :
    ListAdapter<ProductInListVO, ProductViewHolder>(ProductInListDiffUtil()) {

    interface Listener {
        fun onClickProduct(holder: ProductViewHolder, productId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ProductInListDiffUtil : DiffUtil.ItemCallback<ProductInListVO>() {
        override fun areItemsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean =
            oldItem.guid == newItem.guid

        override fun areContentsTheSame(
            oldItem: ProductInListVO,
            newItem: ProductInListVO
        ): Boolean =
            oldItem == newItem

    }
}