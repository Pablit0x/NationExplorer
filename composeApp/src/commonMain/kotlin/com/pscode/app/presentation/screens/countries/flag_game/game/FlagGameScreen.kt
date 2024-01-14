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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.pscode.app.utils.Constants.NUMBER_OF_ROUNDS
import org.koin.compose.koinInject

class FlagGameScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<FlagGameViewModel>()

        val navigator = LocalNavigator.currentOrThrow

        val round by viewModel.round.collectAsState()
        val currentRoundData by viewModel.currentRoundData.collectAsState()
        val isGameDataReady by viewModel.isGameDataReady.collectAsState()
        val selectedFlag by viewModel.selectedFlag.collectAsState()
        val isCorrectSelection by viewModel.isSelectionCorrect.collectAsState()
        val userScore by viewModel.userScore.collectAsState()
        val quizButtonState by viewModel.quizButtonState.collectAsState()
        val isScoreVisible by viewModel.isScoreVisible.collectAsState()
        val isQuizButtonVisible by viewModel.isQuizButtonVisible.collectAsState()
        val currentStopWatchTime by viewModel.currentStopWatchTime.collectAsState()
        val personalBestScore by viewModel.personalBestScore.collectAsState()
        val hasNewPersonalBest by viewModel.hasNewPersonalBest.collectAsState()
        val isUsernameInputDialogVisible by viewModel.isUsernameInputDialogVisible.collectAsState()

        val isGameDisplayed by remember { derivedStateOf { !isScoreVisible || !isUsernameInputDialogVisible } }
        val scrollState = rememberScrollState()

        LaunchedEffect(isGameDataReady) {
            if (isGameDataReady) {
                viewModel.startNewGame()
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.clear()
            }
        }

        Scaffold(topBar = { FlagGameScreenTopBar(navigator = navigator) }) { innerPadding ->
            currentRoundData?.let { currentRound ->

                AnimatedVisibility(visible = isGameDisplayed, enter = fadeIn(), exit = fadeOut()) {

                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(state = scrollState)
                            .padding(innerPadding)
                            .navigateBackOnDrag(onNavigateBack = { navigator.pop() }),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CustomLinearProgressIndicator(
                            currentRound = round,
                            numberOfRounds = NUMBER_OF_ROUNDS,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        RoundHeadlineText(
                            hintText = SharedRes.string.pick_the_flag,
                            countryName = currentRound.targetCountry.name
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            AnimatedStopwatch(
                                timeString = currentStopWatchTime, fontFamily = FontFamily.Monospace
                            )
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2), modifier = Modifier.height(310.dp)
                        ) {
                            items(items = currentRound.options) { option ->
                                FlagGameOption(
                                    flagUrl = option.flagUrl,
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
                            showButton = isQuizButtonVisible,
                            quizButtonState = quizButtonState,
                            modifier = Modifier.fillMaxWidth(0.3f).height(50.dp)
                        )
                    }
                }

                UsernameInputDialog(
                    isOpen = isUsernameInputDialogVisible,
                    onNextClicked = viewModel::setUserName
                )

                GameResultsDialog(
                    userScore = userScore,
                    currentStopWatchTime = currentStopWatchTime,
                    oldPersonalBest = personalBestScore,
                    isOpen = isScoreVisible,
                    hasNewPersonalBest = hasNewPersonalBest,
                    onEndClicked = { navigator.pop() },
                    onRestartClicked = { viewModel.startNewGame() },
                    navigateToLeaderboard = { navigator.replace(item = LeaderboardScreen()) })

            }
        }
    }
}