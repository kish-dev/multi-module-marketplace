package com.software.core_navigation_impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.software.core_navigation_impl.di.FeatureInjectorProxy
import com.software.feature_products_impl.presentation.views.ProductsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateProduct()
    }

    private fun navigateProduct() {
        FeatureInjectorProxy.initFeatureProductsDI()
        val newFragment = ProductsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, newFragment, ProductsFragment::class.java.simpleName)
            .addToBackStack(null)
            .commit()
    }
}
