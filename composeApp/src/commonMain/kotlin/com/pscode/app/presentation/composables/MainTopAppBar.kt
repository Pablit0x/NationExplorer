package com.pscode.app.presentation.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.navigator.Navigator
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen
import com.pscode.app.presentation.screens.countries.overview.SearchWidgetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navigator: Navigator,
    scrollBehavior: TopAppBarScrollBehavior,
    searchWidgetState: SearchWidgetState,
    isFiltering: Boolean,
    isFavourite: Boolean,
    setFavourite: () -> Unit,
    onToggleFavourite: () -> Unit,
    onFilterClicked: () -> Unit,
    searchTextState: TextFieldValue,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    selectedCountry: CountryOverview? = null,
) {
    if (navigator.lastItem is OverviewScreen) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                DefaultTopAppBar(
                    navigator = navigator,
                    onSearchClicked = onSearchTriggered,
                    onFilterClicked = onFilterClicked,
                    onToggleFavourite = onToggleFavourite,
                    setFavourite = setFavourite,
                    isFiltering = isFiltering,
                    isFavourite = isFavourite,
                    scrollBehavior = scrollBehavior,
                    selectedCountry = selectedCountry
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
            onFilterClicked = onFilterClicked,
            onToggleFavourite = onToggleFavourite,
            setFavourite = setFavourite,
            isFiltering = isFiltering,
            isFavourite = isFavourite,
            scrollBehavior = scrollBehavior,
            selectedCountry = selectedCountry,
        )
    }
}