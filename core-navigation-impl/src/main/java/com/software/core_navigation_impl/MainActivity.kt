package com.software.core_navigation_impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.software.core_navigation_impl.databinding.ActivityMainBinding
import com.software.core_navigation_impl.di.FeatureInjectorProxy
import com.software.core_navigation_impl.di.components.CoreNavigationComponent
import com.software.core_navigation_impl.di.components.DaggerNavigationDependenciesComponent
import com.software.core_network_impl.di.CoreNetworkComponent
import com.software.feature_add_product_impl.presentation.views.AddProductFragment
import com.software.feature_api.ConnectionStateApi
import com.software.feature_api.wrappers.ConnectionStatus
import com.software.feature_pdp_impl.presentation.views.PDPFragment
import com.software.feature_products_impl.presentation.views.ProductsFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectionStateApi: ConnectionStateApi

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        CoreNavigationComponent.initAndGet(
            DaggerNavigationDependenciesComponent.builder()
                .networkApi(CoreNetworkComponent.initAndGet(this.applicationContext))
                .build()
        )?.inject(this)
        restoreComponents()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeConnectionState()
        navigateProduct()
    }

    private fun observeConnectionState() {
        with(binding) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    connectionStateApi.connectionStatusFlow.collect {
                        val internetAndConnectionTV =
                            connectionProblems.internetErrorAndConnectionTV
                        when (it) {
                            is ConnectionStatus.Connected -> {
                                connectionProblems.root.isVisible = false
                            }

                            is ConnectionStatus.ConnectionError -> {
                                internetAndConnectionTV.text = getString(R.string.connection_error)
                                connectionProblems.root.isVisible = true
                            }

                            is ConnectionStatus.InternetError -> {
                                internetAndConnectionTV.text = getString(R.string.connection_error)
                                connectionProblems.root.isVisible = true
                            }
                        }
                    }
                }
            }
        }
    }


    private fun navigateProduct() {
        if (FeatureInjectorProxy.isFirstAppLaunch) {
            FeatureInjectorProxy.initFeatureProductsDI(this.applicationContext)
            val newFragment = ProductsFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    newFragment as Fragment,
                    ProductsFragment::class.java.simpleName
                )
                .addToBackStack(ProductsFragment::class.java.simpleName)
                .commit()
            FeatureInjectorProxy.isFirstAppLaunch = false
        } else {
            supportFragmentManager.restoreBackStack("")
        }
    }

    private fun restoreComponents() {
        FeatureInjectorProxy.initFeatureProductsDI(this.applicationContext)
        if (!FeatureInjectorProxy.isFirstAppLaunch) {
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
