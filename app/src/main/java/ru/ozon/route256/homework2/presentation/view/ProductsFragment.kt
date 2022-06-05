package ru.ozon.route256.homework2.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.databinding.FragmentProductsBinding
import ru.ozon.route256.homework2.di.ServiceLocator
import ru.ozon.route256.homework2.presentation.adapters.ProductsAdapter
import ru.ozon.route256.homework2.presentation.viewHolders.ProductViewHolder
import ru.ozon.route256.homework2.presentation.viewModel.ProductViewModel
import ru.ozon.route256.homework2.presentation.viewModel.viewModelCreator
import ru.ozon.route256.homework2.presentation.viewObject.UiState

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding
        get() = _binding!!

    private val productViewModel: ProductViewModel by viewModelCreator {
        ProductViewModel(
            ServiceLocator().productListInteractor,
            ServiceLocator().dispatcherViewModel
        )
    }
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(object : ProductsAdapter.Listener {
            override fun onClickProduct(holder: ProductViewHolder, productId: String) {
                productViewModel.addViewCount(productId)
                val pdpFragment = PDPFragment.newInstance(productId)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, pdpFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        productViewModel.getProducts()
    }

    private fun initViews() {
        with(binding) {
            productsRV.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = productsAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                productViewModel.getProducts()
            }

            addProductButton.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, AddProductFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun initObservers() {
        productViewModel.productLD.observe(viewLifecycleOwner) {
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
                }

                is UiState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    productsAdapter.submitList(it.value)
                }
            }
        }
    }
}
