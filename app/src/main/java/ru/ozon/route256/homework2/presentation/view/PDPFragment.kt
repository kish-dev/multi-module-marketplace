package ru.ozon.route256.homework2.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.di.ServiceLocator
import ru.ozon.route256.homework2.presentation.adapters.ProductImageAdapter
import ru.ozon.route256.homework2.presentation.viewModel.PDPViewModel
import ru.ozon.route256.homework2.presentation.viewModel.viewModelCreator
import ru.ozon.route256.homework2.presentation.viewObject.ProductVO

class PDPFragment : Fragment() {

    private var productImageRV: RecyclerView? = null
    private var nameTV: AppCompatTextView? = null
    private var priceTV: AppCompatTextView? = null
    private var ratingView: AppCompatRatingBar? = null

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
    ): View? {
        return inflater.inflate(R.layout.pdp_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initObservers()
        productId?.let { pdpViewModel.getProduct(productId!!) }
    }

    private fun initObservers() {
        pdpViewModel.productLD.observe(viewLifecycleOwner) {
            initProduct(it)
        }
    }

    private fun initProduct(product: ProductVO?) {
        product?.apply {
            productImageAdapter.submitList(images)
            nameTV?.text = name
            priceTV?.text = price
            ratingView?.rating = rating.toFloat()
        }
    }

    private fun initView(view: View) {
        view.apply {
            productImageRV = findViewById(R.id.productImageRV)
            nameTV = findViewById(R.id.nameTV)
            priceTV = findViewById(R.id.priceTV)
            ratingView = findViewById(R.id.ratingView)
        }

        productImageRV?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = productImageAdapter
        }
        snapHelper.attachToRecyclerView(productImageRV)
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