package com.pscode.app.presentation.screens.countries.flag_game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.presentation.composables.CustomLinearProgressIndicator
import com.pscode.app.presentation.composables.FlagGameOption
import com.pscode.app.presentation.composables.QuizButton
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
        val score by viewModel.points.collectAsState()
        val quizButtonState by viewModel.quizButtonState.collectAsState()
        val showScore by viewModel.showScore.collectAsState()
        val showQuizButton by viewModel.showQuizButton.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(isDataReady) {
            if (isDataReady) {
                viewModel.startNewGame()
            }
        }

        roundData?.let { currentRound ->

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
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(16.dp)
                )


                AnimatedVisibility(showScore) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = "$score/10", fontSize = 32.sp)
                }

                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
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
                                    viewModel.setSelectedFlag(flagUrl = flagUrl)
                                    viewModel.checkAnswer()
                                })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))


                QuizButton(
                    showButton = showQuizButton,
                    quizButtonState = quizButtonState,
                    navigateHome = { navigator.pop() })
            }
        }
    }
}