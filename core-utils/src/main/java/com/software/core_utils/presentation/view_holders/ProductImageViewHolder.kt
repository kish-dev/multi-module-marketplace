package com.software.core_utils.presentation.view_holders

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestBuilder
import com.software.core_utils.R
import com.software.core_utils.presentation.common.setImageFromUrl

class ProductImageViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    var productIV: AppCompatImageView? = null

    init {
        itemView.apply {
            productIV = findViewById(R.id.productIV)
        }
    }

    fun bind(image: String) {
        productIV?.setImageFromUrl(image)

    }
}