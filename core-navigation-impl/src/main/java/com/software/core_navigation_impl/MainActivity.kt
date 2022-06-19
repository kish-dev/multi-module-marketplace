package com.software.core_navigation_impl

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.software.core_navigation_impl.di.FeatureInjectorProxy
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import com.software.feature_products_impl.presentation.views.ProductsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureInjectorProxy.initFeatureProductsDI()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateProduct()
    }

    private fun navigateProduct() {
        if (FeatureInjectorProxy.isFirst) {
            val newFragment = ProductsFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    newFragment,
                    ProductsFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
            FeatureInjectorProxy.isFirst = false
        } else {
            supportFragmentManager.restoreBackStack("")
        }
    }
}
