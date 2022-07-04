package com.software.feature_products_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.card.MaterialCardView
import com.software.core_utils.presentation.adapters.ProductImageAdapter
import com.software.core_utils.presentation.common.debounceClick
import com.software.core_utils.presentation.common.setImageFromUrl
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import com.software.feature_products_impl.presentation.views.ProductsBucketButton
import kotlin.random.Random

class ProductViewHolder(
    itemView: View,
    private val listener: com.software.feature_products_impl.presentation.adapters.ProductsAdapter.Listener
) : RecyclerView.ViewHolder(itemView) {

    private val productImageAdapter: ProductImageAdapter by lazy {
        ProductImageAdapter()
    }

    private val snapHelper: SnapHelper by lazy {
        PagerSnapHelper()
    }

    private var productImageRV: RecyclerView? = null
    private var nameTV: AppCompatTextView? = null
    private var priceTV: AppCompatTextView? = null
    private var ratingView: AppCompatRatingBar? = null
    private var viewsCountTV: AppCompatTextView? = null
    private var cardView: MaterialCardView? = null
    private var productsBucketButton: ProductsBucketButton? = null

    init {
        itemView.apply {
            productImageRV = findViewById(R.id.productIV)
            nameTV = findViewById(R.id.nameTV)
            priceTV = findViewById(R.id.priceTV)
            ratingView = findViewById(R.id.ratingView)
            cardView = findViewById(R.id.materialCardView)
            viewsCountTV = findViewById(R.id.viewsCountTV)
            productsBucketButton = findViewById(R.id.productsBucketButton)
        }
    }

    fun bind(productInListVO: ProductInListVO) {

        //TODO добавить в VO модельки количество товаров в корзине, хранить в репе, мерджить после запроса
        //TODO возвращать через интерактор и инитить стейт кнопки
        productsBucketButton?.setOnClickListener {
            it.debounceClick {
                productsBucketButton?.init()
                productsBucketButton?.setBucketState(Random.nextBoolean())
            }
        }

        cardView?.setOnClickListener {
            it.debounceClick {
                listener.onClickProduct(this, productInListVO.guid)
            }
        }
        
        productImageRV?.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productImageAdapter
        }
        snapHelper.attachToRecyclerView(productImageRV)
        productImageAdapter.submitList(productInListVO.images)
        nameTV?.text = productInListVO.name
        priceTV?.text = productInListVO.price
        ratingView?.rating = productInListVO.rating.toFloat()
        viewsCountTV?.text = productInListVO.viewsCount.toString()
    }
}
