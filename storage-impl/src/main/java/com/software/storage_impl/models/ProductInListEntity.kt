package com.software.storage_impl.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductInListEntity(
    var guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    var viewsCount: Int,
): Parcelable
