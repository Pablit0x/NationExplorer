package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject

class LeaderboardScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = koinInject<LeaderboardViewModel>()
        val results by viewModel.results.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        val silverGradient = listOf(Color(192, 192, 192), Color(169, 169, 169))
        val goldGradient = listOf(Color(255, 215, 0), Color(255, 165, 0))
        val bronzeGradient = listOf(Color(205, 127, 50), Color(139, 69, 19))


        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                stickyHeader {
                    Column(
                        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            TopRankItem(
                                rank = 2,
                                result = results[1],
                                borderGradientColors = silverGradient,
                                modifier = Modifier.weight(1f).height(160.dp)
                            )

                            TopRankItem(
                                rank = 1,
                                result = results[0],
                                borderGradientColors = goldGradient,
                                modifier = Modifier.weight(1f).height(180.dp)
                            )

                            TopRankItem(
                                rank = 3,
                                result = results[2],
                                borderGradientColors = bronzeGradient,
                                modifier = Modifier.weight(1f).height(140.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        LeaderboardHeader(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .background(color = MaterialTheme.colorScheme.background)
                        )
                    }
                }

                itemsIndexed(items = results) { index, result ->
                    LeaderboardListItem(
                        rank = index + 1,
                        result = result,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}