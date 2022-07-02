package com.software.feature_products_impl.di.components

import android.content.Context
import android.util.Log
import com.software.core_utils.di.PerFeature
import com.software.feature_products_impl.di.modules.InteractorModule
import com.software.feature_products_impl.di.modules.ProductsFeatureDependencies
import com.software.feature_products_impl.di.modules.RepositoryModule
import com.software.feature_products_impl.presentation.views.ProductsFragment
import dagger.BindsInstance
import dagger.Component

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

        fun initAndGet(
            productsFeatureDependencies: ProductsFeatureDependencies,
            appContext: Context
        ): ProductsFeatureComponent? =
            when (productsFeatureComponent) {
                null -> {
                    synchronized(this) {
                        when (productsFeatureComponent) {
                            null -> {
                                productsFeatureComponent = DaggerProductsFeatureComponent.builder()
                                    .appContext(appContext)
                                    .productsFeatureDependencies(productsFeatureDependencies)
                                    .build()
                            }
                        }
                        productsFeatureComponent
                    }
                }

                else -> {
                    productsFeatureComponent
                }

            }

        fun get(): ProductsFeatureComponent? =
            when (productsFeatureComponent) {
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

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder
        fun productsFeatureDependencies(productsFeatureDependencies: ProductsFeatureDependencies): Builder
        fun build(): ProductsFeatureComponent
    }

    abstract fun inject(fragment: ProductsFragment)

}
