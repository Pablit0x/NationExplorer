package com.pscode.app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.pscode.app.AndroidApp
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

actual class NetworkConnectivity {

    actual val context: Any
        get() = AndroidApp.INSTANCE.applicationContext


    private val androidContext = context as Context
    private val connectivityManager =
        androidContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    actual fun observeNetworkStatus(): Flow<Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(Status.Available) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(Status.Unavailable) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(Status.Unavailable) }
                }
            }
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }.distinctUntilChanged()
    }
}
