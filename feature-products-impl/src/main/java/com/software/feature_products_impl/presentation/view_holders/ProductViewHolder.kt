package com.software.feature_products_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.software.core_utils.presentation.common.setImageFromUrl
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO

class ProductViewHolder(
    itemView: View,
    private val listener: com.software.feature_products_impl.presentation.adapters.ProductsAdapter.Listener
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

        productIV?.setImageFromUrl(productInListVO.image)

        nameTV?.text = productInListVO.name
        priceTV?.text = productInListVO.price
        ratingView?.rating = productInListVO.rating.toFloat()
        viewsCountTV?.text = productInListVO.viewsCount.toString()
    }
}