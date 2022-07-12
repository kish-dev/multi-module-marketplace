package com.software.feature_products_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.view_objects.ProductsListItem

class TitleProductProductsListItemViewHolder(
    itemView: View,
) : BaseProductsListItemViewHolder<ProductsListItem.TitleProductVO>(itemView) {

    private var titleProductVO: ProductsListItem.TitleProductVO? = null

    private var titleTV: AppCompatTextView? = null

    init {
        itemView.apply {
            titleTV = findViewById(R.id.title__tv)
        }
    }

    override fun bind(item: ProductsListItem.TitleProductVO) {
        this.titleProductVO = item

        titleTV?.text = item.headerText
    }

}