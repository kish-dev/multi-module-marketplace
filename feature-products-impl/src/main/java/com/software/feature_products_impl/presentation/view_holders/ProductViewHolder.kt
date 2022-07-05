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
import com.software.feature_products_impl.presentation.views.ProductsBucketButton
import kotlin.random.Random

class ProductViewHolder(
    itemView: View,
    private val listener: com.software.feature_products_impl.presentation.adapters.ProductsAndTitlesAdapter.Listener,
    private val productImageAdapter: ProductImageAdapter,
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
    private var productsBucketButton: ProductsBucketButton? = null

    private var productInListVO: BaseProductsTitleModel.ProductInListVO? = null

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

        cardView?.setOnClickListener {
            it.debounceClick {
                productInListVO?.guid?.let {
                        product -> listener.onClickProduct(this, product)
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
    }
}
