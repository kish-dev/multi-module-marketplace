package com.software.feature_products_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.card.MaterialCardView
import com.software.core_utils.presentation.adapters.ProductImageAdapter
import com.software.core_utils.presentation.common.debounceClick
import com.software.feature_products_impl.R
import com.software.feature_products_impl.presentation.view_objects.ProductInListVO
import com.software.feature_products_impl.presentation.views.ProductsBucketButton
import kotlin.random.Random

class ProductViewHolder(
    itemView: View,
    private val listener: com.software.feature_products_impl.presentation.adapters.ProductsAdapter.Listener,
    private val productImageAdapter: ProductImageAdapter,
) : RecyclerView.ViewHolder(itemView) {

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

    private var productInListVO: ProductInListVO? = null

    init {
        itemView.apply {
            productImageRV = findViewById(R.id.productIV)
            nameTV = findViewById(R.id.nameTV)
            priceTV = findViewById(R.id.priceTV)
            ratingView = findViewById(R.id.ratingView)
            cardView = findViewById(R.id.materialCardView)
            viewsCountTV = findViewById(R.id.viewsCountTV)
            productsBucketButton = findViewById(R.id.productsBucketButton)

            initRecycler()
            initListeners()
        }
    }

    private fun initListeners() {

        //TODO добавить в VO модельки количество товаров в корзине, хранить в репе, мерджить после запроса
        //TODO возвращать через интерактор и инитить стейт кнопки
        productsBucketButton?.setOnClickListener {
            it.debounceClick {
                productsBucketButton?.init()
                productsBucketButton?.setBucketState(Random.nextBoolean())
            }
        }

        //TODO fix misscache
        cardView?.setOnClickListener {
            it.debounceClick {
                productInListVO?.guid?.let { product -> listener.onClickProduct(this, product) }
            }
        }
    }

    private fun initRecycler() {
        productImageRV?.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    .apply {
                        recycleChildrenOnDetach = true
                    }

            adapter = productImageAdapter
        }
        snapHelper.attachToRecyclerView(productImageRV)
    }

    fun bind(productInListVO: ProductInListVO) {
        this.productInListVO = productInListVO

        productImageAdapter.submitList(productInListVO.images)
        nameTV?.text = productInListVO.name
        priceTV?.text = productInListVO.price
        ratingView?.rating = productInListVO.rating.toFloat()
        viewsCountTV?.text = productInListVO.viewsCount.toString()
    }
}
