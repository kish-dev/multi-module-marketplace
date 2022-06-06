package ru.ozon.route256.homework2.presentation.viewHolders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.presentation.viewHolders.base.BaseViewHolder

class ProductImageViewHolder(
    itemView: View
) : BaseViewHolder(itemView) {

    private var productIV: AppCompatImageView? = null

    init {
        itemView.apply {
            productIV = findViewById(R.id.productIV)
        }
    }

    fun bind(image: String) {
        productIV?.let {
            glideWith()
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(it)
        }

    }
}