package com.software.feature_add_product_impl.presentation.view_objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ImageLinkVO(
    val guid: String,
    var imageLink: String,
) : Parcelable {

}
