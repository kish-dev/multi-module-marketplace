package com.software.core_navigation_impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.software.core_navigation_impl.di.FeatureInjectorProxy
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import com.software.feature_products_impl.presentation.views.ProductsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        restoreComponents()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateProduct()
    }

    private fun navigateProduct() {
        if (FeatureInjectorProxy.isFirst) {
            FeatureInjectorProxy.initFeatureProductsDI(this.applicationContext)
            val newFragment = ProductsFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    newFragment,
                    ProductsFragment::class.java.simpleName
                )
                .addToBackStack(ProductsFragment::class.java.simpleName)
                .commit()
            FeatureInjectorProxy.isFirst = false
        } else {
            supportFragmentManager.restoreBackStack("")
        }
    }

    private fun restoreComponents() {
        FeatureInjectorProxy.initFeatureProductsDI(this.applicationContext)
        if (!FeatureInjectorProxy.isFirst) {
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                val entry = supportFragmentManager.getBackStackEntryAt(i)
                entry.name?.let {
                    when (it) {
                        PDPFragment::class.java.simpleName ->
                            FeatureInjectorProxy.initFeaturePDPDI(this.applicationContext)

                        AddProductFragment::class.java.simpleName ->
                            FeatureInjectorProxy.initFeatureAddProductDI(this.applicationContext)
                    }
                }
            }
        }
    }
}
