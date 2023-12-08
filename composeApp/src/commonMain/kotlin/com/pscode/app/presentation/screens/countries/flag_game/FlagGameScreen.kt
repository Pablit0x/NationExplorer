package com.pscode.app.presentation.screens.countries.flag_game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.presentation.composables.CustomLinearProgressIndicator
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
        var correct by remember { mutableStateOf(false) }

        LaunchedEffect(Unit){
            delay(3000)
            viewModel.startNewGame()
        }

        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomLinearProgressIndicator(
                currentRound = round, modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (correct) "Correct" else "Incorrect",
                color = if (correct) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(32.dp))


            roundData?.let {

                Column(
                    modifier = Modifier.fillMaxHeight(0.7f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = it.targetCountry.name)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        it.options.forEach { option ->
                            KamelImage(resource = asyncPainterResource(option.flagUrl),
                                contentDescription = null,
                                modifier = Modifier.size(75.dp).clickable {
                                    correct = viewModel.checkAnswer(
                                        selectedFlag = option.flagUrl,
                                        targetCountry = it.targetCountry
                                    )
                                })
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(32.dp))



            Button(onClick = {
                viewModel.nextStage()
            }) {
                Text(text = "Next Stage")
            }

        }
    }
}