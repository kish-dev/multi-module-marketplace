package com.software.feature_products_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.view_objects.BaseProductsTitleModel

class TitleProductViewHolder(
    itemView: View,
) : BaseViewHolder<BaseProductsTitleModel.TitleProductVO>(itemView) {

    private var titleProductVO: BaseProductsTitleModel.TitleProductVO? = null

    private var titleTV: AppCompatTextView? = null

    init {
        itemView.apply {
            titleTV = findViewById(R.id.title__tv)
        }
    }

    override fun bind(item: BaseProductsTitleModel.TitleProductVO) {
        this.titleProductVO = item

        titleTV?.text = item.headerText
    }

}