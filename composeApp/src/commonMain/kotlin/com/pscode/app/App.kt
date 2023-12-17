package com.pscode.app

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.pscode.app.di.connectivityModule
import com.pscode.app.di.dataModule
import com.pscode.app.di.viewModelModule
import com.pscode.app.presentation.composables.MainTopAppBar
import com.pscode.app.presentation.composables.isScrollingUp
import com.pscode.app.presentation.screens.countries.flag_game.game.FlagGameScreen
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen
import com.pscode.app.presentation.screens.countries.overview.OverviewViewModel
import com.pscode.app.presentation.screens.countries.overview.SearchWidgetState
import com.pscode.app.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()


    KoinApplication(moduleList = { listOf(dataModule, viewModelModule, connectivityModule) }) {

        val overviewViewModel = koinInject<OverviewViewModel>()
        val searchWidgetState by overviewViewModel.searchWidgetState.collectAsState()
        val searchText by overviewViewModel.searchText.collectAsState()
        val selectedCountryName by overviewViewModel.selectedCountryName.collectAsState()

        AppTheme {
            Navigator(
                screen = OverviewScreen(
                    viewModel = overviewViewModel, onShowSnackBar = { errorMsg ->
                        scope.launch {
                            snackBarHostState.showSnackbar(message = errorMsg)
                        }
                    }, lazyListState = listState
                )
            ) { navigator ->
                Scaffold(modifier = Modifier.then(
                    if (navigator.lastItem is OverviewScreen) {
                        Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                    } else {
                        Modifier
                    }
                ), topBar = {
                    MainTopAppBar(navigator = navigator,
                        selectedCountryName = selectedCountryName,
                        scrollBehavior = scrollBehavior,
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchText,
                        onTextChange = { overviewViewModel.onSearchTextChange(text = it) },
                        onCloseClicked = { overviewViewModel.onSearchWidgetChange(newState = SearchWidgetState.CLOSED) },
                        onSearchTriggered = { overviewViewModel.onSearchWidgetChange(newState = SearchWidgetState.OPENED) })
                }, floatingActionButton = {
                    if (navigator.lastItem is OverviewScreen) {
                        ExtendedFloatingActionButton(
                            onClick = {
                                navigator.push(item = FlagGameScreen())
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Games,
                                    contentDescription = "Play games"
                                )
                            },
                            text = {
                                Text(text = SharedRes.string.play_game)
                            },
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            expanded = listState.isScrollingUp(),
                        )
                    }
                }, snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) { innerPadding ->
                    SlideTransition(
                        navigator = navigator,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}




