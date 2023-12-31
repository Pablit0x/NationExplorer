package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.CountryOverview
import com.pscode.app.presentation.screens.countries.detail.DetailScreen
import com.pscode.app.presentation.screens.countries.flag_game.game.FlagGameScreen
import com.pscode.app.presentation.screens.countries.flag_game.leaderboard.LeaderboardScreen
import com.pscode.app.presentation.screens.countries.overview.OverviewScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    navigator: Navigator,
    onSearchClicked: () -> Unit,
    onFilterClicked: () -> Unit,
    onToggleFavourite: () -> Unit,
    setFavourite: () -> Unit,
    isFiltering: Boolean,
    isFavourite: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    selectedCountry: CountryOverview? = null
) {
    val currentScreen = navigator.lastItem

    var topBarTitle: String = SharedRes.string.app_name
    var actions: @Composable() (RowScope.() -> Unit) = {}
    var navigationIcon: @Composable () -> Unit = {}

    when (currentScreen) {
        is OverviewScreen -> {
            topBarTitle = SharedRes.string.countries
            actions = {
                IconButton(onClick = onFilterClicked) {
                    BadgedBox(badge = {
                        if (isFiltering) {
                            Badge(modifier = Modifier.size(3.dp))
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Tune, contentDescription = "Filter")
                    }
                }

                IconButton(onClick = onSearchClicked) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        }

        is FlagGameScreen -> {
            topBarTitle = SharedRes.string.flag_matcher
            navigationIcon = {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "Navigate back"
                    )
                }
            }
        }

        is DetailScreen -> {

            LaunchedEffect(Unit) {
                setFavourite()
            }

            topBarTitle = selectedCountry?.name ?: SharedRes.string.unknown
            navigationIcon = {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "Navigate back"
                    )
                }
            }
            actions = {
                IconButton(onClick = onToggleFavourite) {
                    if (isFavourite) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Remove country from favourites"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Add country to favourites"
                        )
                    }
                }
            }
        }

        is LeaderboardScreen -> {
            topBarTitle = SharedRes.string.leaderboard
            navigationIcon = {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "Navigate back"
                    )
                }
            }
        }
    }

    CenterAlignedTopAppBar(
        title = {
            AutoResizedText(
                text = topBarTitle,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        },
        actions = actions,
        navigationIcon = navigationIcon,
        scrollBehavior = if (currentScreen is OverviewScreen) scrollBehavior else null
    )
}