package ru.ozon.route256.homework2.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.databinding.PdpFragmentBinding
import ru.ozon.route256.homework2.di.ServiceLocator
import ru.ozon.route256.homework2.presentation.adapters.ProductImageAdapter
import ru.ozon.route256.homework2.presentation.viewModel.PDPViewModel
import ru.ozon.route256.homework2.presentation.viewModel.viewModelCreator
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO
import ru.ozon.route256.homework2.presentation.viewObject.UiState

class PDPFragment : Fragment() {

    private var _binding: PdpFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val productImageAdapter: ProductImageAdapter by lazy {
        ProductImageAdapter()
    }

    private val snapHelper: SnapHelper by lazy {
        PagerSnapHelper()
    }

    private var productId: String? = null

    private val pdpViewModel: PDPViewModel by viewModelCreator {
        PDPViewModel(
            ServiceLocator().productDetailInteractor,
            ServiceLocator().dispatcherViewModel
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            productId = it.getString(EXTRA_PRODUCT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PdpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        productId?.let { pdpViewModel.getProduct(productId!!) }
    }

    private fun initObservers() {
        pdpViewModel.productLD.observe(viewLifecycleOwner) {
            when(it) {
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
                    initProduct(it.value)
                }
            }
        }
    }

    private fun initProduct(product: ProductVO?) {
        product?.apply {
            with(binding) {
                productImageAdapter.submitList(images)
                nameTV.text = name
                priceTV.text = price
                ratingView.rating = rating.toFloat()
            }
        }
    }

    private fun initView() {
        with(binding) {
            productImageRV.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = productImageAdapter
            }
            snapHelper.attachToRecyclerView(productImageRV)

            swipeRefreshLayout.setOnRefreshListener {
                productId?.let { pdpViewModel.getProduct(productId!!) }
            }
        }


    }

    companion object {

        private const val EXTRA_PRODUCT_ID = "extra_product_id"

        @JvmStatic
        fun newInstance(productId: String): PDPFragment = PDPFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_PRODUCT_ID, productId)
            }
        }
    }
}