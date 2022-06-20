package com.software.feature_pdp_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.software.core_utils.presentation.common.setImageFromUrl
import com.software.feature_pdp_impl.R

class ProductImageViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private var productIV: AppCompatImageView? = null

    init {
        itemView.apply {
            productIV = findViewById(R.id.productIV)
        }
    }

    fun bind(image: String) {
        productIV?.setImageFromUrl(image)
    }
}