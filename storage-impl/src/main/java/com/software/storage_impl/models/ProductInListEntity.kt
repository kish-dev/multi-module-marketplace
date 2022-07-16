package com.software.storage_impl.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductInListEntity(
    var guid: String,
    val images: List<String>,
    val name: String,
    val price: String,
    val rating: Double,
    var isFavorite: Boolean,
    var isInCart: Boolean,
    var viewsCount: Int,
) : Parcelable
