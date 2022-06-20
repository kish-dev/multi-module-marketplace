package com.software.feature_products_impl.di.components

import android.util.Log
import com.software.core_utils.di.PerFeature
import com.software.feature_products_impl.di.modules.InteractorModule
import com.software.feature_products_impl.di.modules.ProductsFeatureDependencies
import com.software.feature_products_impl.di.modules.RepositoryModule
import com.software.feature_products_impl.presentation.views.ProductsFragment
import dagger.Component
import java.lang.RuntimeException

@Component(
    modules = [InteractorModule::class, RepositoryModule::class],
    dependencies = [ProductsFeatureDependencies::class]
)
@PerFeature
abstract class ProductsFeatureComponent {

    companion object {

        @Volatile
        var productsFeatureComponent: ProductsFeatureComponent? = null
            private set

        @Synchronized
        fun initAndGet(productsFeatureDependencies: ProductsFeatureDependencies): ProductsFeatureComponent? =
            when(productsFeatureComponent) {
                null -> {
                    productsFeatureComponent = DaggerProductsFeatureComponent.builder()
                        .productsFeatureDependencies(productsFeatureDependencies)
                        .build()
                    productsFeatureComponent
                }

                else -> {
                    productsFeatureComponent
                }

            }

        fun get(): ProductsFeatureComponent? =
            when(productsFeatureComponent) {
                null -> {
                    throw RuntimeException("You must call 'initAndGet(productFeatureDependencies: ProductFeatureDependencies)' method")
                }

                else -> {
                    productsFeatureComponent
                }
            }

        fun reset() {
            productsFeatureComponent = null
            Log.d("initFeatureProductsDI", "reset: ")
        }

    }

    abstract fun inject(fragment: ProductsFragment)

}
