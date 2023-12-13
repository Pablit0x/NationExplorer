package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject

class LeaderboardScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinInject<LeaderboardViewModel>()
        val results by viewModel.results.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            itemsIndexed(items = results) { index, result ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${index + 1}")
                    Text(text = result.username)
                    Text(text = "${result.score}")
                    Text(text = result.time)
                }
                Divider()
            }
        }
    }
}