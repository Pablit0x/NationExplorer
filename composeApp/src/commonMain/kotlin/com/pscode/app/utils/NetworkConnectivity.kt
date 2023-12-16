package com.pscode.app.utils

import kotlinx.coroutines.flow.Flow


expect class NetworkConnectivity() {
    val context : Any
    fun observeNetworkStatus() : Flow<Status>
}

enum class Status {
    Available, Unavailable, Lost
}