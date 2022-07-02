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


    override fun getConnectionStatusFlow(): StateFlow<ConnectionStatus> {
        return connectionStatus
    }

    private var _connectionStatus: MutableStateFlow<ConnectionStatus> =
        MutableStateFlow(ConnectionStatus.Success)
    var connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    @Volatile
    private var networkCapabilities: NetworkCapabilities? = null

    @Volatile
    private var network: Network? = null

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    init {
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    this@ConnectionStateImpl.network = network
                    networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                    updateConnection()
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    _connectionStatus.value = (ConnectionStatus.ConnectionError)
                    this@ConnectionStateImpl.network = network
                    networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                }

                override fun onLost(network: Network) {
                    networkCapabilities = null
                    this@ConnectionStateImpl.network = network
                    _connectionStatus.value = (ConnectionStatus.ConnectionError)
                }

                override fun onUnavailable() {
                    networkCapabilities = null
                    network = null
                    _connectionStatus.value = (ConnectionStatus.ConnectionError)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    this@ConnectionStateImpl.networkCapabilities = networkCapabilities
                    updateConnection()
                }
            })
    }

    @Synchronized
    private fun updateConnection() {
        if (network == null) {
            _connectionStatus.value = ConnectionStatus.ConnectionError
        }

        when (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true &&
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
            true -> {
                _connectionStatus.value = ConnectionStatus.Success
            }

            else -> {
                _connectionStatus.value = ConnectionStatus.InternetError
            }
        }
    }
}