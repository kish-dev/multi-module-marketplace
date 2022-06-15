package com.software.feature_pdp_impl.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.software.feature_pdp_impl.R

class ProductImageAdapter : ListAdapter<String, com.software.feature_pdp_impl.presentation.view_holders.ProductImageViewHolder>(
    StringDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.software.feature_pdp_impl.presentation.view_holders.ProductImageViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.product_image_item, parent, false)
        return com.software.feature_pdp_impl.presentation.view_holders.ProductImageViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: com.software.feature_pdp_impl.presentation.view_holders.ProductImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class StringDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }
}
