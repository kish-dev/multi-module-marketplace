package ru.ozon.route256.homework2.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.presentation.viewHolders.ProductViewHolder
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO

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