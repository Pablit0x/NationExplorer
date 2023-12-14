package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.domain.model.Result
import com.pscode.app.presentation.composables.AnimatedBorderCard
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.utils.Constants

@Composable
fun TopRankItem(
    rank: Int, result: Result, borderGradientColors: List<Color>, modifier: Modifier = Modifier
) {

    AnimatedBorderCard(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 10),
        brush = Brush.verticalGradient(borderGradientColors)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "#$rank",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            AutoResizedText(
                text = result.username,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = SharedRes.string.score_leaderboard,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "${result.score}/${Constants.NUMBER_OF_ROUNDS}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = SharedRes.string.time_leaderboard,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = result.time,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}