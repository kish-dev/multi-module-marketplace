package ru.ozon.route256.homework2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.presentation.viewHolders.ProductImageViewHolder

class ProductImageAdapter : ListAdapter<String, ProductImageViewHolder>(StringDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.product_image_item, parent, false)
        return ProductImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class StringDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }
}
