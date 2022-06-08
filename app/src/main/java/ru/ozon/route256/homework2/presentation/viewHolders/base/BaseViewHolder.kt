package ru.ozon.route256.homework2.presentation.viewHolders.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun glideWith(): RequestManager {
        return Glide.with(itemView)
    }
}