package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject

class LeaderboardScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = koinInject<LeaderboardViewModel>()
        val results by viewModel.results.collectAsState()
        val currentUser = viewModel.currentUser

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            stickyHeader {
                LeaderboardHeader(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
            }

            itemsIndexed(items = results) { index, result ->
                LeaderboardListItem(
                    rank = index + 1,
                    result = result,
                    currentUser = currentUser,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}