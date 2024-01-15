package com.pscode.app.presentation.screens.countries.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.pscode.app.presentation.composables.AutoResizedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopBar(
    title: String,
    isCountryFavourite: Boolean,
    onToggleFavourite: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navigator: Navigator
) {

    CenterAlignedTopAppBar(
        title = {
            AutoResizedText(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        },
        actions = {
            IconButton(onClick = onToggleFavourite) {
                if (isCountryFavourite) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        tint = Color.Red,
                        contentDescription = "Remove country from favourites"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Add country to favourites"
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { navigator.pop() }) {
                Icon(
                    imageVector = Icons.Default.NavigateBefore,
                    contentDescription = "Navigate back"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}