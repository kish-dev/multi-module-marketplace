package com.software.core_navigation_impl.navigation

import androidx.fragment.app.Fragment
import com.software.feature_pdp_api.PDPNavigationApi
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import javax.inject.Inject

class PDPNavigationImpl @Inject constructor() : PDPNavigationApi {

    override fun isClosed(fragment: Fragment): Boolean {
        return if (fragment.javaClass.simpleName != PDPFragment::class.simpleName) {
            fragment.activity?.supportFragmentManager?.findFragmentByTag(PDPFragment::class.java.simpleName) == null
        } else {
            true
        }
    }
}
