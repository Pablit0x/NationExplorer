package com.pscode.app.presentation.screens.countries.flag_game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.pscode.app.presentation.composables.CustomLinearProgressIndicator
import org.koin.compose.koinInject

class FlagGameScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinInject<FlagGameViewModel>()
        val gameStage by viewModel.gameStage.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomLinearProgressIndicator(
                gameStage = gameStage, modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                viewModel.nextStage()
            }) {
                Text(text = "Next Stage")
            }

        }
    }
}