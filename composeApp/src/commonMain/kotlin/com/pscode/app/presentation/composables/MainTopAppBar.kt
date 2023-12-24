package com.pscode.app.presentation.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.navigator.Navigator
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen
import com.pscode.app.presentation.screens.countries.overview.SearchWidgetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navigator: Navigator,
    scrollBehavior: TopAppBarScrollBehavior,
    searchWidgetState: SearchWidgetState,
    searchTextState: TextFieldValue,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    selectedCountryName: String? = null,
) {
    if (navigator.lastItem is OverviewScreen) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                DefaultTopAppBar(
                    navigator = navigator,
                    onSearchClicked = onSearchTriggered,
                    scrollBehavior = scrollBehavior,
                    selectedCountryName = selectedCountryName
                )
            }

            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    textFieldValue = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked
                )
            }
        }
    } else {
        DefaultTopAppBar(
            navigator = navigator,
            onSearchClicked = onSearchTriggered,
            scrollBehavior = scrollBehavior,
            selectedCountryName = selectedCountryName
        )
    }
}