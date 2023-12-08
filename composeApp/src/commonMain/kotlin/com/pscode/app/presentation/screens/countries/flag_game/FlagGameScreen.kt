package com.pscode.app.presentation.screens.countries.flag_game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.presentation.composables.CustomLinearProgressIndicator
import com.pscode.app.presentation.composables.FlagGameOption
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

class FlagGameScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<FlagGameViewModel>()

        val round by viewModel.round.collectAsState()
        val roundData by viewModel.roundData.collectAsState()
        val isDataReady by viewModel.isDataReady.collectAsState()

        val selectedFlag by viewModel.selectedFlag.collectAsState()
        val isCorrectSelection by viewModel.isSelectionCorrect.collectAsState()
        val isSelectionMade by viewModel.isSelectionMade.collectAsState()

        var showNextButton by remember { mutableStateOf(false) }

        LaunchedEffect(isDataReady) {
            if (isDataReady) {
                viewModel.startNewGame()
            }
        }

        roundData?.let { currentRound ->

            LaunchedEffect(key1 = selectedFlag) {
                if (selectedFlag != null) {
                    showNextButton = true
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomLinearProgressIndicator(
                    currentRound = round, modifier = Modifier.fillMaxWidth(0.7f)
                )

                Text(
                    text = currentRound.targetCountry.name.uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(items = currentRound.options) { option ->
                            FlagGameOption(flagUrl = option.flagUrl,
                                isCorrectFlag = option.flagUrl == currentRound.targetCountry.flagUrl,
                                isSelectedFlag = option.flagUrl == selectedFlag,
                                isCorrectSelection = isCorrectSelection,
                                isSelectionMade = selectedFlag != null,
                                onClick = { flagUrl ->
                                    if (!isSelectionMade) {
                                        viewModel.setSelectedFlag(flagUrl = flagUrl)
                                        viewModel.checkAnswer()
                                    }
                                })
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    AnimatedVisibility(showNextButton) {
                        Button(onClick = {
                            showNextButton = false
                            viewModel.nextStage()
                        }) {
                            Text(text = "Next")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowRightAlt,
                                contentDescription = "Next"
                            )
                        }
                    }
                }
            }
        }
    }
}