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
import com.software.feature_products_impl.presentation.view_objects.BaseProductsTitleModel
import com.software.feature_products_impl.presentation.views.ProductsCartButtonView

class ProductViewHolder(
    itemView: View,
    private val listener: com.software.feature_products_impl.presentation.adapters.ProductsAndTitlesAdapter.Listener,
    private val productImageAdapter: ProductImageAdapter,
    private val nestedRecycledViewPool: RecyclerView.RecycledViewPool
) : BaseViewHolder<BaseProductsTitleModel.ProductInListVO>(itemView) {

    private val snapHelper: SnapHelper by lazy {
        PagerSnapHelper()
    }

    private var productImageRV: RecyclerView? = null
    private var nameTV: AppCompatTextView? = null
    private var priceTV: AppCompatTextView? = null
    private var ratingView: AppCompatRatingBar? = null
    private var viewsCountTV: AppCompatTextView? = null
    private var cardView: MaterialCardView? = null
    private var productsCartButtonView: ProductsCartButtonView? = null

    private var productInListVO: BaseProductsTitleModel.ProductInListVO? = null

    init {
        itemView.apply {
            productImageRV = findViewById(R.id.productIV)
            nameTV = findViewById(R.id.nameTV)
            priceTV = findViewById(R.id.priceTV)
            ratingView = findViewById(R.id.ratingView)
            cardView = findViewById(R.id.materialCardView)
            viewsCountTV = findViewById(R.id.viewsCountTV)
            productsCartButtonView = findViewById(R.id.productsCartButton)

            initRecycler()
            initListeners()
        }
    }

    private fun initListeners() {
        productsCartButtonView?.setOnClickListener { view ->
            view.debounceClick {
                productInListVO?.let {
                    productsCartButtonView?.init()
                    listener.onChangeCartState(this, it.guid, !it.isInCart)
                }
            }
        }

        cardView?.setOnClickListener {
            it.debounceClick {
                productInListVO?.guid?.let { product ->
                    listener.onClickProduct(this, product)
                }
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
            setRecycledViewPool(nestedRecycledViewPool)
        }
        snapHelper.attachToRecyclerView(productImageRV)
    }


    override fun bind(item: BaseProductsTitleModel.ProductInListVO) {
        this.productInListVO = item

        productImageAdapter.submitList(item.images)
        nameTV?.text = item.name
        priceTV?.text = item.price
        ratingView?.rating = item.rating.toFloat()
        viewsCountTV?.text = item.viewsCount.toString()
        productsCartButtonView?.setCartState(item.isInCart)
    }

    fun bindIsInCartState(item: BaseProductsTitleModel.ProductInListVO) {
        productsCartButtonView?.setCartState(item.isInCart)
    }

    fun bindViewsCount(item: BaseProductsTitleModel.ProductInListVO) {
        viewsCountTV?.text = item.viewsCount.toString()
    }
}
