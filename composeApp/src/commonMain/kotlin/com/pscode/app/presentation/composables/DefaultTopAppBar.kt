package com.pscode.app.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    navigator: Navigator,
    onSearchClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    selectedCountryName: String? = null
) {
    val currentScreen = navigator.lastItem

    CenterAlignedTopAppBar(title = {
        Text(
            text = selectedCountryName ?: SharedRes.string.countries
        )
    }, actions = {
        when (currentScreen) {
            is OverviewScreen -> {
                IconButton(onClick = onSearchClicked) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }

            else -> {}
        }
    }, navigationIcon = {
        when (currentScreen) {
            is OverviewScreen -> {}
            else -> {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        }
    }, scrollBehavior = if (currentScreen is OverviewScreen) scrollBehavior else null
    )
}