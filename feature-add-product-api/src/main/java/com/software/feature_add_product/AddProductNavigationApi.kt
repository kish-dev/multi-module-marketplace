package com.software.feature_add_product

import androidx.fragment.app.Fragment

interface AddProductNavigationApi {
    fun isClosed(fragment: Fragment): Boolean
}