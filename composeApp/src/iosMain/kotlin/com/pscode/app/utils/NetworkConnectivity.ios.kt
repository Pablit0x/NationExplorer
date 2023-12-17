package com.pscode.app.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import platform.Network.*
import platform.darwin.*

actual class NetworkConnectivity {
    actual fun observeNetworkStatus(): Flow<Status> {
        return callbackFlow {
            val monitor = nw_path_monitor_create()
            nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
            nw_path_monitor_set_update_handler(monitor) { path ->
                val status = when (nw_path_get_status(path)) {
                    nw_path_status_satisfied  -> Status.Available
                    nw_path_status_unsatisfied -> Status.Unavailable
                    else -> Status.Unavailable
                }
                launch { send(status) }
            }

            nw_path_monitor_start(monitor)

            awaitClose {
                nw_path_monitor_cancel(monitor)
            }
        }
    }

    actual val context: Any
        get() = Any()
}