package com.software.feature_pdp_impl.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.software.feature_pdp_impl.R

class PDPCartCountButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defaultStyleAttr: Int = 0,
    defaultStyleRes: Int = 0
) : FrameLayout(context, attrs, defaultStyleAttr, defaultStyleRes) {

    private var view: View? = null
    private var inCartCL: ConstraintLayout? = null
    private var minusIV: AppCompatImageView? = null
    private var plusIV: AppCompatImageView? = null
    private var countTV: AppCompatTextView? = null
    private var resultPriceTV: AppCompatTextView? = null
    private var addToCart: AppCompatTextView? = null

    init {
        view = View.inflate(context, R.layout.pdp_cart_count_view, this)
        init(view)
    }

    private fun init(view: View?) {
        view?.apply {
            inCartCL = findViewById(R.id.in_cart__cl)
            minusIV = findViewById(R.id.minus_count__iv)
            plusIV = findViewById(R.id.plus_count__iv)
            countTV = findViewById(R.id.count__tv)
            resultPriceTV = findViewById(R.id.result_price__tv)
            addToCart = findViewById(R.id.add_to_cart__tv)
        }
    }

    fun initListeners(plusListener: () -> Unit, minusListener: () -> Unit) {
        plusIV?.setOnClickListener { plusListener() }
        minusIV?.setOnClickListener { minusListener() }

        addToCart?.setOnClickListener { plusListener() }
    }

    //TODO fix
    fun setCartCountState(count: Int?, price: String) {
        when(count) {
            null, 0 -> {
                inCartCL?.isVisible = false
                addToCart?.isVisible = true
            }

            else -> {
                addToCart?.isVisible = false
                countTV?.text = count.toString()
                resultPriceTV?.text = (price.toDouble() * (count * 2)).toString()
                inCartCL?.isVisible = true
            }
        }
    }
}
