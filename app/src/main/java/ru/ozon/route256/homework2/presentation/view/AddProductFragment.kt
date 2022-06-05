package ru.ozon.route256.homework2.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.ozon.route256.homework2.R
import ru.ozon.route256.homework2.databinding.FragmentAddProductBinding
import ru.ozon.route256.homework2.di.ServiceLocator
import ru.ozon.route256.homework2.presentation.viewModel.AddProductViewModel
import ru.ozon.route256.homework2.presentation.viewModel.viewModelCreator
import ru.ozon.route256.homework2.presentation.viewObject.UiState
import ru.ozon.route256.homework2.presentation.viewObject.createProduct

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding
        get() = _binding!!

    private val addProductViewModel: AddProductViewModel by viewModelCreator {
        AddProductViewModel(
            ServiceLocator().addProductInteractor,
            ServiceLocator().dispatcherViewModel
        )
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

    private fun initObservers() {
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