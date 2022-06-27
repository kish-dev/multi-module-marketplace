package com.software.core_navigation_impl.navigation

import androidx.fragment.app.Fragment
import com.software.core_navigation_impl.R
import com.software.core_navigation_impl.di.FeatureInjectorProxy
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.presentation.views.ProductsFragment
import javax.inject.Inject

class ProductsNavigationImpl @Inject constructor() : ProductsNavigationApi {

    override fun isClosed(fragment: Fragment): Boolean {
        return if (fragment.javaClass.simpleName != ProductsFragment::class.simpleName) {
            fragment.activity?.supportFragmentManager?.findFragmentByTag(ProductsFragment::class.java.simpleName) == null
        } else {
            true
        }
    }

    override fun navigateToPDP(fragment: Fragment, guid: String) {
        if (fragment.activity != null) {
            fragment.activity?.let {
                FeatureInjectorProxy.initFeaturePDPDI(it.applicationContext)
                val newFragment = PDPFragment.newInstance(guid)
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        newFragment as Fragment,
                        PDPFragment::class.java.simpleName
                    )
                    .addToBackStack(fragment.javaClass.simpleName)
                    .commit()
            }
        } else {
            throw NullPointerException(
                "Fragment($fragment) activity is null," +
                        " fragment.activity=${fragment.activity}"
            )
        }
    }

    override fun navigateToAddProduct(fragment: Fragment) {
        if (fragment.activity != null) {
            fragment.activity?.let {
                FeatureInjectorProxy.initFeatureAddProductDI(it.applicationContext)
                val newFragment = AddProductFragment()
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        newFragment as Fragment,
                        AddProductFragment::class.java.simpleName
                    )
                    .addToBackStack(fragment.javaClass.simpleName)
                    .commit()
            }
        } else {
            throw NullPointerException(
                "Fragment($fragment) activity is null," +
                        " fragment.activity=${fragment.activity}"
            )
        }

    }
}
