package com.software.feature_pdp_impl.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.software.core_utils.presentation.viewModels.viewModelCreator
import com.software.feature_pdp_api.PDPNavigationApi
import com.software.core_utils.R
import com.software.core_utils.presentation.common.UiState
import com.software.feature_pdp_impl.databinding.PdpFragmentBinding
import com.software.feature_pdp_impl.di.components.PDPFeatureComponent
import com.software.feature_pdp_impl.domain.interactors.ProductDetailUseCase
import com.software.feature_pdp_impl.presentation.adapters.ProductImageAdapter
import com.software.feature_pdp_impl.presentation.view_models.PDPViewModel
import com.software.feature_pdp_impl.presentation.view_objects.ProductVO
import javax.inject.Inject

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

    @Inject
    lateinit var pdpInteractor: ProductDetailUseCase

    @Inject
    lateinit var pdpNavigationApi: PDPNavigationApi

    private val pdpViewModel: PDPViewModel by viewModelCreator {
        PDPViewModel(pdpInteractor)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        PDPFeatureComponent.pdpFeatureComponent?.inject(this)
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

    override fun onPause() {
        if(isRemoving) {
            if(pdpNavigationApi.isClosed(this)) {
                PDPFeatureComponent.reset()
            }
        }
        super.onPause()
    }

    companion object {

        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private val TAG = PDPFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(productId: String): PDPFragment = PDPFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_PRODUCT_ID, productId)
            }
        }
    }
}