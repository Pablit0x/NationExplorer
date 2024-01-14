package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.presentation.composables.FullScreenLoadingIndicator
import com.pscode.app.presentation.composables.LeaderboardPodium
import com.pscode.app.presentation.composables.navigateBackOnDrag
import org.koin.compose.koinInject

class LeaderboardScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinInject<LeaderboardViewModel>()
        val results by viewModel.results.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val navigator = LocalNavigator.currentOrThrow


        Scaffold(topBar = { LeaderBoardScreenTopBar(navigator = navigator) }) { innerPadding ->
            if (isLoading) {
                FullScreenLoadingIndicator(innerPadding = innerPadding,
                    onNavigateBackOnDrag = { navigator.popUntilRoot() })
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                        .navigateBackOnDrag(onNavigateBack = { navigator.popUntilRoot() })
                ) {

                    LeaderboardPodium(
                        topThreeResults = results.subList(0, 2),
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    LeaderboardHeader(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                            .background(color = MaterialTheme.colorScheme.background)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(items = results) { index, result ->
                            LeaderboardListItem(
                                rank = index + 1,
                                result = result,
                                isCurrentUser = viewModel.isCurrentUser(userId = result.userId),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}