package com.software.feature_products_impl.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.software.core_utils.R
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.presentation.viewModels.viewModelCreator
import com.software.feature_api.ProductsApi
import com.software.feature_products_api.ProductsNavigationApi
import com.software.feature_products_impl.databinding.FragmentProductsBinding
import com.software.feature_products_impl.di.components.ProductsFeatureComponent
import com.software.feature_products_impl.domain.interactors.LoadWithWorkersUseCase
import com.software.feature_products_impl.domain.interactors.ProductListUseCase
import com.software.feature_products_impl.presentation.adapters.ProductsAdapter
import com.software.feature_products_impl.presentation.view_holders.ProductViewHolder
import com.software.feature_products_impl.presentation.view_models.ProductsViewModel
import javax.inject.Inject

class ProductsFragment : Fragment() {

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

    private val productsViewModel: ProductsViewModel by viewModelCreator {
        ProductsViewModel(
            productsInteractor,
            loadInteractor,
            productsNavigationApi
        )
    }
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(object : ProductsAdapter.Listener {
            override fun onClickProduct(holder: ProductViewHolder, productId: String) {
                productsViewModel.addViewCount(productId)
                productsNavigationApi.navigateToPDP(this@ProductsFragment, productId)
            }
        })
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

    private fun initViews() {
        with(binding) {
            productsRV.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = productsAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                productsViewModel.getProducts()
            }

            addProductButton.setOnClickListener {
                productsNavigationApi.navigateToAddProduct(this@ProductsFragment)
            }
        }
    }

    private fun initObservers() {
        productsViewModel.productLD.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading, is UiState.Init -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }

                is UiState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.loading_error),
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.e(TAG, "UiState is Error because of ${it.throwable.message}")
                }

                is UiState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    productsAdapter.submitList(it.value)
                }
            }
        }

        productsViewModel.lastChangedProduct.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading, is UiState.Init -> {
                }

                is UiState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.loading_error),
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.e(TAG, "UiState is Error because of ${it.throwable.message}")
                }

                is UiState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    productsAdapter.currentList
                        .filter { prev -> prev.guid == it.value.guid }
                        .map { prev -> prev.viewsCount = it.value.viewsCount }
                    val position = productsAdapter.currentList.indexOf(it.value)
                    productsAdapter.notifyItemChanged(position)
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
        super.onPause()
    }

    companion object {
        private val TAG = ProductsFragment::class.java.simpleName
    }
}