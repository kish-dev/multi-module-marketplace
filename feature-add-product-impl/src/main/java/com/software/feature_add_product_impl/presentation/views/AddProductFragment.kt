package com.software.feature_add_product_impl.presentation.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.software.core_utils.R
import com.software.core_utils.presentation.common.UiState
import com.software.core_utils.presentation.fragments.BaseFragment
import com.software.core_utils.presentation.view_models.viewModelCreator
import com.software.core_utils.presentation.view_objects.ProductVO
import com.software.core_utils.presentation.view_objects.createProduct
import com.software.core_utils.presentation.view_objects.isNotBlank
import com.software.feature_add_product.AddProductNavigationApi
import com.software.feature_add_product_impl.databinding.FragmentAddProductBinding
import com.software.feature_add_product_impl.di.components.AddProductFeatureComponent
import com.software.feature_add_product_impl.domain.interactors.AddProductUseCase
import com.software.feature_add_product_impl.presentation.view_models.AddProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductFragment : BaseFragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var addProductInteractor: AddProductUseCase

    @Inject
    lateinit var addProductNavigationApi: AddProductNavigationApi

    override val viewModel: AddProductViewModel by viewModelCreator {
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
        saveChanges()
    }

    private fun showRestoreDialog(product: ProductVO) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.restore_dialog_title)
            .setPositiveButton(R.string.dialog_positive_button) { _, _ ->
                fillViews(product)
            }
            .setNegativeButton(R.string.dialog_negative_button) { _, _ ->
                showToast(getString(R.string.restore_products_error))
            }
            .show()
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
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.restoreState.collect {
                    when (it) {
                        is UiState.Loading -> {
                            binding.swipeRefreshLayout.isRefreshing = true
                        }

                        is UiState.Error -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                        }

                        is UiState.Success -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            when {
                                it.value.isNotBlank() -> {
                                    showRestoreDialog(it.value)
                                }
                            }
                        }

                        is UiState.Init -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }
        }

        viewModel.addProductState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }

                is UiState.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showToast(getString(R.string.add_product_error))
                }

                is UiState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    parentFragmentManager.popBackStack()
                }

                is UiState.Init -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun fillViews(product: ProductVO) {
        with(binding) {
            nameEditText.setText(product.name)
            priceEditText.setText(product.price)
            descriptionEditText.setText(product.description)

            val imagesStringBuilder = StringBuilder()
            product.images.forEach {
                imagesStringBuilder.append("$it ")
            }

            imageLinkEditText.setText(imagesStringBuilder)
            ratingBar.rating = product.rating.toFloat()

            showToast(getString(R.string.restore_product_success))
        }
    }

    private fun saveChanges() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    delay(1000)
                    with(binding) {
                        viewModel.addChangedProduct(
                            createProduct(
                                name = nameEditText.text!!.toString(),
                                description = descriptionEditText.text!!.toString(),
                                price = priceEditText.text!!.toString(),
                                image = imageLinkEditText.text!!.toString(),
                                rating = ratingBar.rating.toDouble()
                            )
                        )
                    }
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
                    viewModel.addProduct(product)
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
