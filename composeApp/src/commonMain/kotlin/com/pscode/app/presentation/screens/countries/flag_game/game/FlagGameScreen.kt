package com.pscode.app.presentation.screens.countries.flag_game.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.pscode.app.SharedRes
import com.pscode.app.presentation.composables.AnimatedStopwatch
import com.pscode.app.presentation.composables.CustomLinearProgressIndicator
import com.pscode.app.presentation.composables.FlagGameOption
import com.pscode.app.presentation.composables.GameResultsDialog
import com.pscode.app.presentation.composables.QuizButton
import com.pscode.app.presentation.composables.RoundHeadlineText
import com.pscode.app.presentation.composables.UsernameInputDialog
import com.pscode.app.presentation.composables.navigateBackOnDrag
import com.pscode.app.presentation.screens.countries.flag_game.leaderboard.LeaderboardScreen
import org.koin.compose.koinInject

class FlagGameScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<FlagGameViewModel>()

        val navigator = LocalNavigator.currentOrThrow

        val round by viewModel.round.collectAsState()
        val roundData by viewModel.roundData.collectAsState()
        val isDataReady by viewModel.isDataReady.collectAsState()
        val selectedFlag by viewModel.selectedFlag.collectAsState()
        val isCorrectSelection by viewModel.isSelectionCorrect.collectAsState()
        val score by viewModel.points.collectAsState()
        val quizButtonState by viewModel.quizButtonState.collectAsState()
        val showScore by viewModel.showScore.collectAsState()
        val showQuizButton by viewModel.showQuizButton.collectAsState()
        val stopWatchTime by viewModel.stopWatchTime.collectAsState()
        val personalBest by viewModel.personalBest.collectAsState()
        val isNewPersonalBest by viewModel.isNewPersonalBest.collectAsState()
        val showUsernameInputDialog by viewModel.showUsernameInputDialog.collectAsState()

        LaunchedEffect(isDataReady) {
            if (isDataReady) {
                viewModel.startNewGame()
            }
        }

        roundData?.let { currentRound ->

            UsernameInputDialog(
                isOpen = showUsernameInputDialog, onNextClicked = viewModel::setUserName
            )

            GameResultsDialog(score = "$score/${FlagGameViewModel.NUMBER_OF_ROUNDS}",
                time = stopWatchTime,
                pbMessage = if (isNewPersonalBest) {
                    SharedRes.string.new_personal_best
                } else {
                    SharedRes.string.old_personal_best.format(oldPb = "(${personalBest.first}/${FlagGameViewModel.NUMBER_OF_ROUNDS}, ${personalBest.second})")
                },
                isOpen = showScore,
                newBest = isNewPersonalBest,
                onEndClicked = { navigator.pop() },
                onRestartClicked = {
                    viewModel.startNewGame()
                },
                navigateToLeaderboard = {
                    navigator.push(item = LeaderboardScreen())
                })

            AnimatedVisibility(
                visible = !showScore, enter = fadeIn(), exit = fadeOut()
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                        .navigateBackOnDrag(onNavigateBack = { navigator.pop() })
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            CustomLinearProgressIndicator(
                                currentRound = round,
                                numberOfRounds = FlagGameViewModel.NUMBER_OF_ROUNDS,
                                modifier = Modifier.fillMaxWidth(0.7f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            RoundHeadlineText(
                                hintText = SharedRes.string.pick_the_flag,
                                countryName = currentRound.targetCountry.name,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                AnimatedStopwatch(
                                    timeString = stopWatchTime, fontFamily = FontFamily.Monospace
                                )
                            }

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2), modifier = Modifier.height(310.dp)
                            ) {
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

                            QuizButton(
                                showButton = showQuizButton,
                                quizButtonState = quizButtonState,
                                modifier = Modifier.fillMaxWidth(0.3f).height(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}