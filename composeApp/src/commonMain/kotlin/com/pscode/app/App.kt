package com.pscode.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.pscode.app.di.connectivityModule
import com.pscode.app.di.dataModule
import com.pscode.app.di.viewModelModule
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen
import com.pscode.app.presentation.theme.AppTheme
import com.pscode.app.presentation.theme.CustomAppTheme
import com.pscode.app.utils.WindowSize
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication


@Composable
internal fun App(windowSize: WindowSize) {
    KoinApplication(moduleList = { listOf(dataModule, viewModelModule, connectivityModule) }) {


        CustomAppTheme(windowSize = windowSize) {
            Navigator(
                screen = OverviewScreen()
            ) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}

fun debugBuild() {
    Napier.base(DebugAntilog())
}



