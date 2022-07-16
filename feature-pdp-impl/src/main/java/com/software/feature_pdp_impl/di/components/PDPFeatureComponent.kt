package com.software.feature_pdp_impl.di.components

import com.software.core_utils.di.PerFeature
import com.software.feature_pdp_impl.di.modules.PDPFeatureDependencies
import com.software.feature_pdp_impl.di.modules.PDPInteractorModule
import com.software.feature_pdp_impl.di.modules.RepositoryModule
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import dagger.Component

@Component(
    modules = [PDPInteractorModule::class, RepositoryModule::class],
    dependencies = [PDPFeatureDependencies::class]
)
@PerFeature
abstract class PDPFeatureComponent {

    companion object {

        @Volatile
        var pdpFeatureComponent: PDPFeatureComponent? = null
            private set

        fun initAndGet(pdpFeatureDependencies: PDPFeatureDependencies): PDPFeatureComponent? =
            when (pdpFeatureComponent) {
                null -> {
                    synchronized(this) {
                        when (pdpFeatureComponent) {
                            null -> {
                                pdpFeatureComponent = DaggerPDPFeatureComponent.builder()
                                    .pDPFeatureDependencies(pdpFeatureDependencies)
                                    .build()
                            }
                        }

                        pdpFeatureComponent
                    }
                }

                else -> {
                    pdpFeatureComponent
                }

            }

        fun get(): PDPFeatureComponent? =
            when (pdpFeatureComponent) {
                null -> {
                    throw RuntimeException("You must call 'initAndGet(pdpFeatureDependencies: PDPFeatureDependencies)' method")
                }

                else -> {
                    pdpFeatureComponent
                }
            }

        fun reset() {
            pdpFeatureComponent = null
        }

    }

    abstract fun inject(fragment: PDPFragment)

}
