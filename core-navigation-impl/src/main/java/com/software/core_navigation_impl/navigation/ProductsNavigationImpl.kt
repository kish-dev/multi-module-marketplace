package com.software.core_navigation_impl.navigation

import android.util.Log
import androidx.fragment.app.Fragment
import com.software.core_navigation_impl.R
import com.software.core_navigation_impl.di.FeatureInjectorProxy
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.presentation.views.ProductsFragment
import javax.inject.Inject

class ProductsNavigationImpl @Inject constructor(): ProductsNavigationApi {

    override fun isClosed(fragment: Fragment): Boolean {
        return if(fragment.javaClass.simpleName != ProductsFragment::class.simpleName) {
            var isInBackStack = false
            fragment.activity?.supportFragmentManager?.let {
                for(i in 0 until it.backStackEntryCount) {
                    if(it.getBackStackEntryAt(i).name == ProductsFragment::class.java.simpleName) {
                        isInBackStack = true
                    }
                }
                if(it.findFragmentByTag(ProductsFragment::class.java.simpleName) != null) {
                    isInBackStack = true
                }
            }
            isInBackStack
        } else {
            true
        }
    }

    override fun navigateToPDP(fragment: Fragment, guid: String) {
        FeatureInjectorProxy.initFeaturePDPDI()
        val newFragment = PDPFragment.newInstance(guid)
        fragment.activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentContainer, newFragment, PDPFragment::class.java.simpleName)
            ?.addToBackStack(fragment.javaClass.simpleName)
            ?.commit()
    }

    override fun navigateToAddProduct(fragment: Fragment) {
        FeatureInjectorProxy.initFeatureAddProductDI()
        val newFragment = AddProductFragment()
        fragment.activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentContainer, newFragment, AddProductFragment::class.java.simpleName)
            ?.addToBackStack(fragment.javaClass.simpleName)
            ?.commit()
    }
}
