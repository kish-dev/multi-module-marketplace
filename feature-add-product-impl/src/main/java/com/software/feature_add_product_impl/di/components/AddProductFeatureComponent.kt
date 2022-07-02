package com.software.feature_add_product_impl.di.components

import com.software.core_utils.di.PerFeature
import com.software.feature_add_product_impl.di.modules.AddProductFeatureDependencies
import com.software.feature_add_product_impl.di.modules.AddProductInteractorModule
import com.software.feature_add_product_impl.di.modules.RepositoryModule
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import dagger.Component

@Component(
    modules = [AddProductInteractorModule::class, RepositoryModule::class],
    dependencies = [AddProductFeatureDependencies::class]
)
@PerFeature
abstract class AddProductFeatureComponent {

    companion object {

        @Volatile
        var addProductFeatureComponent: AddProductFeatureComponent? = null
            private set

        fun initAndGet(addProductFeatureDependencies: AddProductFeatureDependencies): AddProductFeatureComponent? =
            when (addProductFeatureComponent) {
                null -> {
                    synchronized(this) {
                        when (addProductFeatureComponent) {
                            null -> {
                                addProductFeatureComponent =
                                    DaggerAddProductFeatureComponent.builder()
                                        .addProductFeatureDependencies(addProductFeatureDependencies)
                                        .build()
                            }
                        }
                        addProductFeatureComponent
                    }
                }

                else -> {
                    addProductFeatureComponent
                }

            }

        fun get(): AddProductFeatureComponent? =
            when (addProductFeatureComponent) {
                null -> {
                    throw RuntimeException("You must call 'initAndGet(addProductFeatureDependencies: AddProductFeatureDependencies)' method")
                }

                else -> {
                    addProductFeatureComponent
                }
            }

        fun reset() {
            addProductFeatureComponent = null
        }

    }

    abstract fun inject(fragment: AddProductFragment)

}
