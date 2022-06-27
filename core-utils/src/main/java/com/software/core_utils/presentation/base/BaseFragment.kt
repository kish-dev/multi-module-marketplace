package com.software.core_utils.presentation.base

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.software.core_utils.R
import com.software.feature_api.ConnectionStateApi
import com.software.feature_api.wrappers.ConnectionStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var connectionStateApi: ConnectionStateApi

    protected fun observeNetworkConnection(connectionLayout: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                connectionStateApi.getConnectionStatusFlow().collect {
                    val internetAndConnectionTV = connectionLayout.findViewById<AppCompatTextView>(
                        R.id.internetErrorAndConnectionTV
                    )
                    when (it) {
                        is ConnectionStatus.Success -> {
                            connectionLayout.isVisible = false
                        }

                        is ConnectionStatus.ConnectionError -> {
                            internetAndConnectionTV.text = getString(R.string.connection_error)
                            connectionLayout.isVisible = true
                        }

                        is ConnectionStatus.InternetError -> {
                            internetAndConnectionTV.text = getString(R.string.connection_error)
                            connectionLayout.isVisible = true
                        }
                    }
                }
            }
        }
    }

}