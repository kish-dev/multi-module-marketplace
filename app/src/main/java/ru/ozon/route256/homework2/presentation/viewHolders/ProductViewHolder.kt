package ru.ozon.route256.homework2.presentation.viewHolders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.card.MaterialCardView
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.presentation.adapters.ProductsAdapter
import ru.ozon.route256.homework2.presentation.viewObject.ProductInListVO

class ProductViewHolder(
    itemView: View,
    private val listener: ProductsAdapter.Listener
) : RecyclerView.ViewHolder(itemView) {

    private var productIV: AppCompatImageView? = null
    private var nameTV: AppCompatTextView? = null
    private var priceTV: AppCompatTextView? = null
    private var ratingView: AppCompatRatingBar? = null
    private var viewsCountTV: AppCompatTextView? = null
    private var cardView: MaterialCardView? = null

    init {
        itemView.apply {
            productIV = findViewById(R.id.productIV)
            nameTV = findViewById(R.id.nameTV)
            priceTV = findViewById(R.id.priceTV)
            ratingView = findViewById(R.id.ratingView)
            cardView = findViewById(R.id.materialCardView)
            viewsCountTV = findViewById(R.id.viewsCountTV)
        }
    }

    fun bind(productInListVO: ProductInListVO) {

        cardView?.setOnClickListener {
            listener.onClickProduct(this, productInListVO.guid)
        }

        productIV?.let {
            Glide.with(itemView)
                .load(productInListVO.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(it)
        }

        nameTV?.text = productInListVO.name
        priceTV?.text = productInListVO.price
        ratingView?.rating = productInListVO.rating.toFloat()
        viewsCountTV?.text = productInListVO.viewsCount.toString()
    }
}