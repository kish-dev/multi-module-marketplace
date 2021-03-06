package com.software.storage_impl.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val guid: String,
    val name: String,
    val price: String,
    val description: String,
    val rating: Double,
    var isFavorite: Boolean,
    var isInCart: Boolean,
    val images: List<String>,
    val weight: Double?,
    var count: Int?,
    val availableCount: Int?,
    val additionalParams: Map<String, String>
) : Parcelable