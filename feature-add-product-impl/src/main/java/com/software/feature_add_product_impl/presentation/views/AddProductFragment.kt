package com.software.feature_add_product_impl.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.software.core_utils.R
import com.software.core_utils.presentation.base.BaseFragment
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.presentation.view_models.viewModelCreator
import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_add_product_impl.databinding.FragmentAddProductBinding
import com.software.feature_add_product_impl.di.components.AddProductFeatureComponent
import com.software.feature_add_product_impl.domain.interactors.AddProductUseCase
import com.software.feature_add_product_impl.presentation.view_models.AddProductViewModel
import com.software.feature_add_product_impl.presentation.view_objects.createProduct
import javax.inject.Inject

class AddProductFragment : BaseFragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var addProductInteractor: AddProductUseCase

    @Inject
    lateinit var addProductNavigationApi: AddProductNavigationApi

    private val addProductViewModel: AddProductViewModel by viewModelCreator {
        AddProductViewModel(addProductInteractor)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AddProductFeatureComponent.addProductFeatureComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onPause() {
        if (isRemoving) {
            if (addProductNavigationApi.isClosed(this)) {
                AddProductFeatureComponent.reset()
            }
        }
        super.onPause()
    }

    private fun initObservers() {
        observeNetworkConnection(binding.connectionProblems)

        addProductViewModel.addProductState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
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
                    Toast.makeText(
                        requireContext(),
                        R.string.add_product_success,
                        Toast.LENGTH_SHORT
                    ).show()
                    parentFragmentManager.popBackStack()
                }

                is UiState.Init -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            addProductButton.setOnClickListener {
                if (isValid()) {
                    val product = createProduct(
                        name = nameEditText.text!!.toString(),
                        description = descriptionEditText.text!!.toString(),
                        price = priceEditText.text!!.toString(),
                        image = imageLinkEditText.text!!.toString(),
                        rating = ratingBar.rating.toDouble()
                    )
                    addProductViewModel.addProduct(product)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_all_strokes),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun isValid(): Boolean {
        var isValid = false
        with(binding) {
            if (nameEditText.text?.isNotBlank() == true &&
                priceEditText.text?.isNotBlank() == true &&
                descriptionEditText.text?.isNotBlank() == true &&
                imageLinkEditText.text?.isNotBlank() == true &&
                ratingBar.rating.isFinite()
            ) {
                isValid = true
            }
        }
        return isValid
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
