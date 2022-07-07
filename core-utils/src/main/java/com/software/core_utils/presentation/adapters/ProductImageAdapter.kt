package com.software.core_utils.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.software.core_utils.R
import com.software.core_utils.presentation.common.ViewTypes
import com.software.core_utils.presentation.view_holders.ProductImageViewHolder

class ProductImageAdapter : ListAdapter<String, ProductImageViewHolder>(
    StringDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.product_image_item, parent, false)
        return ProductImageViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return ViewTypes.IMAGES
    }

    private class StringDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }
}
