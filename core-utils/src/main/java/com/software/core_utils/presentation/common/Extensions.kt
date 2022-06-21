package com.software.core_utils.presentation.common

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.software.core_utils.R

fun AppCompatImageView.setImageFromUrl(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}

fun Context.getDefaultSharedPreferences(): SharedPreferences {
    return this.getSharedPreferences(this.getString(R.string.shared_preferences), 0)
}