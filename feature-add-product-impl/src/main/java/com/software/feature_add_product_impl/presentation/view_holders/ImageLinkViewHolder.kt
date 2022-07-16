package com.software.feature_add_product_impl.presentation.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.software.feature_add_product_impl.R
import com.software.feature_add_product_impl.presentation.adapters.ImageLinkAdapter
import com.software.feature_add_product_impl.presentation.view_objects.ImageLinkVO

class ImageLinkViewHolder(
    itemView: View,
    private val listener: ImageLinkAdapter.ImageLinkListener,
    val textChangeListener: ImageLinkAdapter.RecyclerTextWatcher
) : RecyclerView.ViewHolder(itemView) {

    var imageLinkEditText: TextInputEditText? = null
    var deleteImageView: AppCompatImageView? = null

    init {
        itemView.apply {
            imageLinkEditText = findViewById(R.id.imageLinkEditText)

            deleteImageView = findViewById(R.id.deleteImageView)
            deleteImageView?.setOnClickListener {
                listener.onDeleteClick(adapterPosition, this@ImageLinkViewHolder)

            }
        }
    }

    fun enableTextWatcher() {
        imageLinkEditText?.addTextChangedListener(textChangeListener)
    }

    fun disableTextWatcher() {
        imageLinkEditText?.removeTextChangedListener(textChangeListener)
    }

    fun bind(imageLink: ImageLinkVO) {
        imageLinkEditText?.setText(imageLink.imageLink)
    }
}