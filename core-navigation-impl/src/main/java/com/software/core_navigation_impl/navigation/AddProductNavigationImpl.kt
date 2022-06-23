package com.software.core_navigation_impl.navigation

import androidx.fragment.app.Fragment
import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import javax.inject.Inject

class AddProductNavigationImpl @Inject constructor() : AddProductNavigationApi {

    override fun isClosed(fragment: Fragment): Boolean {
        return if (fragment.javaClass.simpleName != AddProductFragment::class.simpleName) {
            fragment.activity?.supportFragmentManager?.findFragmentByTag(AddProductFragment::class.java.simpleName) == null
        } else {
            true
        }
    }
}
