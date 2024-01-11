package com.pscode.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.pscode.app.di.connectivityModule
import com.pscode.app.di.dataModule
import com.pscode.app.di.viewModelModule
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen
import com.pscode.app.presentation.theme.AppTheme
import org.koin.compose.KoinApplication


@Composable
internal fun App() {
    KoinApplication(moduleList = { listOf(dataModule, viewModelModule, connectivityModule) }) {

        AppTheme {
            Navigator(
                screen = OverviewScreen()
            ) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}




