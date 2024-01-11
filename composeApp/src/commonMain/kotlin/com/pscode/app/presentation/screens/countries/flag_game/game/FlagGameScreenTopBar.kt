package com.pscode.app.presentation.screens.countries.flag_game.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.pscode.app.SharedRes
import com.pscode.app.presentation.composables.AutoResizedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagGameScreenTopBar(navigator: Navigator) {
    CenterAlignedTopAppBar(
        title = {
            AutoResizedText(
                text = SharedRes.string.flag_matcher,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigator.pop() }) {
                Icon(
                    imageVector = Icons.Default.NavigateBefore, contentDescription = "Navigate back"
                )
            }
        },
    )
}