package ru.ozon.route256.homework2.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.di.ServiceLocator
import ru.ozon.route256.homework2.presentation.adapters.ProductsAdapter
import ru.ozon.route256.homework2.presentation.viewHolders.ProductViewHolder
import ru.ozon.route256.homework2.presentation.viewModel.ProductViewModel
import ru.ozon.route256.homework2.presentation.viewModel.viewModelCreator

class ProductsFragment : Fragment() {

    private var productsRecyclerView: RecyclerView? = null
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initObservers()
    }

    private fun initViews(view: View) {
        view.apply {
            productsRecyclerView = findViewById(R.id.productsRV)
        }
        productsRecyclerView?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = productsAdapter
        }
    }

    private fun initObservers() {
        productViewModel.productLD.observe(viewLifecycleOwner) {
            productsAdapter.submitList(it)
        }
    }
}
