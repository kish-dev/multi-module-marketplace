package com.software.feature_products_impl.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.presentation.fragments.BaseFragment
import com.software.core_utils.presentation.view_models.viewModelCreator
import com.software.feature_api.ProductsApi
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.databinding.FragmentProductsBinding
import com.software.feature_products_impl.di.components.ProductsFeatureComponent
import com.software.feature_products_impl.domain.interactors.LoadWithWorkersUseCase
import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.presentation.adapters.ProductsAndTitlesAdapter
import com.software.feature_products_impl.presentation.view_holders.ProductViewHolder
import com.software.feature_products_impl.presentation.view_holders.ViewHolderFactory
import com.software.feature_products_impl.presentation.view_models.ProductsViewModel
import com.software.feature_products_impl.presentation.view_objects.mapToBaseRVModel
import javax.inject.Inject

class ProductsFragment : BaseFragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var productsApi: ProductsApi

    @Inject
    lateinit var productsInteractor: ProductListUseCase

    @Inject
    lateinit var productsNavigationApi: ProductsNavigationApi

    @Inject
    lateinit var loadInteractor: LoadWithWorkersUseCase

    override val viewModel: ProductsViewModel by viewModelCreator {
        ProductsViewModel(
            productsInteractor,
            loadInteractor,
            productsNavigationApi
        )
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.getProducts()
    }

    private val productsAndTitlesAdapter: ProductsAndTitlesAdapter by lazy {
        ProductsAndTitlesAdapter(
            object : ProductsAndTitlesAdapter.Listener {
                override fun onClickProduct(holder: ProductViewHolder, productId: String) {
                    viewModel.addViewCount(productId)
                    productsNavigationApi.navigateToPDP(this@ProductsFragment, productId)
                }

                override fun onChangeCartState(
                    holder: ProductViewHolder,
                    productId: String,
                    inCart: Boolean
                ) {
                    viewModel.updateProductCartState(productId, inCart)
                }
            },
            ViewHolderFactory(),
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ProductsFeatureComponent.productsFeatureComponent?.inject(this)
        productsApi
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.autoUpdateProducts()
        with(binding) {
            swipeRefreshLayout.post {
                swipeRefreshLayout.isRefreshing = true
                swipeRefreshListener.onRefresh()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            productsRV.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = productsAndTitlesAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getProducts()
            }

            addProductButton.setOnClickListener {
                productsNavigationApi.navigateToAddProduct(this@ProductsFragment)
            }
        }
    }

    private fun initObservers() {
        viewModel.productRecyclerLD.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading, is UiState.Init -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }

                is UiState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Log.e(TAG, "UiState is Error because of ${it.throwable.message}")
                }

                is UiState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    val list = context?.let { context ->
                        it.value.mapToBaseRVModel(
                            context
                        )
                    }
                    productsAndTitlesAdapter.submitList(list)
                }
            }
        }
    }

    override fun onPause() {
        if (isRemoving) {
            if (productsNavigationApi.isClosed(this)) {
                ProductsFeatureComponent.reset()
            }
        }
        viewModel.stopAutoUpdate()
        super.onPause()
    }

    companion object {
        private val TAG = ProductsFragment::class.java.simpleName
    }
}