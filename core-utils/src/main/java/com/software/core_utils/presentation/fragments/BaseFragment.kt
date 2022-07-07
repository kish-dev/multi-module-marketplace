package com.software.core_utils.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.software.core_utils.R
import com.software.core_utils.presentation.common.Action
import com.software.core_utils.presentation.view_models.BaseViewModel
import com.software.core_utils.presentation.view_models.viewModelCreator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment: Fragment() {

    protected open val viewModel : BaseViewModel by viewModelCreator {
        BaseViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionStateObservers()
    }

    protected open fun initActionStateObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.action.collect {
                    when (it) {
                        is Action.ShowToast -> {
                            showToast(getString(it.stringId))
                        }
                    }
                }
            }
        }
    }

    protected open fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
