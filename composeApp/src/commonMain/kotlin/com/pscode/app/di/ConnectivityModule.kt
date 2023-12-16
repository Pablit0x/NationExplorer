package com.pscode.app.di

import com.pscode.app.utils.NetworkConnectivity
import org.koin.dsl.module

val connectivityModule = module {
    single<NetworkConnectivity> { NetworkConnectivity() }
}