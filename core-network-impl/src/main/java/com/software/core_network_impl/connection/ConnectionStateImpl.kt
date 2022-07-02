package com.software.core_network_impl.connection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.software.feature_api.ConnectionStateApi
import com.software.feature_api.wrappers.ConnectionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class ConnectionStateImpl @Inject constructor(context: Context) : ConnectionStateApi {

    override val connectionStatusFlow: StateFlow<ConnectionStatus>
        get() = connectionStatus

    private var _connectionStatus: MutableStateFlow<ConnectionStatus> =
        MutableStateFlow(ConnectionStatus.Connected)
    var connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    init {
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    updateConnection(network, connectivityManager.getNetworkCapabilities(network))
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    updateConnection(null, connectivityManager.getNetworkCapabilities(network))
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    updateConnection(null, connectivityManager.getNetworkCapabilities(network))
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    updateConnection(null, connectivityManager.getNetworkCapabilities(null))
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    updateConnection(network, connectivityManager.getNetworkCapabilities(network))
                }
            })
    }

    @Synchronized
    private fun updateConnection(network: Network?, networkCapabilities: NetworkCapabilities?) {
        when {
            network == null || networkCapabilities == null -> {
                _connectionStatus.value = ConnectionStatus.ConnectionError
            }

            (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) -> {
                _connectionStatus.value = ConnectionStatus.Connected

            }

            else -> {
                _connectionStatus.value = ConnectionStatus.InternetError
            }
        }
    }
}