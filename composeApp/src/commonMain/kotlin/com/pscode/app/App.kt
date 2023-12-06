package com.pscode.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.pscode.app.di.dataModule
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen
import com.pscode.app.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication

@Composable
internal fun App() {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    KoinApplication(moduleList = { listOf(dataModule) }) {
        AppTheme {
            Navigator(screen = OverviewScreen(onShowSnackBar = { errorMsg ->
                scope.launch {
                    snackBarHostState.showSnackbar(message = errorMsg)
                }
            })) { navigator ->
                Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) { innerPadding ->
                    SlideTransition(
                        navigator = navigator,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}



