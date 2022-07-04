package com.software.feature_products_impl.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.software.feature_products_impl.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class ProductsBucketButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defaultStyleAttr: Int = 0,
    defaultStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defaultStyleAttr, defaultStyleRes) {

    private var view: View? = null
    private var progressBar: ProgressBar? = null
    private var titleText: AppCompatTextView? = null
    private var cardView: MaterialCardView? = null

    init {
        view = View.inflate(context, R.layout.products_bucket_button_view, this)
        initView(view)
    }

    private fun initView(view: View?) {
        view?.apply {
            progressBar = findViewById(R.id.loading__pb)
            titleText = findViewById(R.id.title__tv)
            cardView = findViewById(R.id.button__cv)
        }
    }

    fun init() {
        progressBar?.progress = 0
        progressBar?.isVisible = true
        titleText?.isVisible = false
    }

    fun setBucketState(isInBucket: Boolean) {
        when(isInBucket) {
            true -> {
                addToBucket()
            }

            else -> {
                removeFromBucket()
            }
        }

    }

    private fun addToBucket() {
        cardView?.setCardBackgroundColor(context.getColor(com.software.core_utils.R.color.purple_200))
        progressBar?.isVisible = false
        titleText?.text = context.getString(com.software.core_utils.R.string.in_the_bucket)
        titleText?.isVisible = true
    }

    private fun removeFromBucket() {
        cardView?.setCardBackgroundColor(context.getColor(com.software.core_utils.R.color.gray))
        progressBar?.isVisible = false
        titleText?.text = view?.context?.getString(com.software.core_utils.R.string.add_to_a_bucket)
        titleText?.isVisible = true
    }
}