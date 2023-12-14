package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes

@Composable
fun LeaderboardHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.border(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            width = 1.dp,
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
        ).background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = SharedRes.string.rank_leaderboard,
            modifier = Modifier.weight(0.5f).border(
                color = MaterialTheme.colorScheme.tertiaryContainer, width = 1.dp
            ).padding(4.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = SharedRes.string.name_leaderboard,
            modifier = Modifier.weight(1f).border(
                color = MaterialTheme.colorScheme.tertiaryContainer, width = 1.dp
            ).padding(4.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = SharedRes.string.score_leaderboard,
            modifier = Modifier.weight(1f).border(
                color = MaterialTheme.colorScheme.tertiaryContainer, width = 1.dp
            ).padding(4.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = SharedRes.string.time_leaderboard,
            modifier = Modifier.weight(1f).border(
                color = MaterialTheme.colorScheme.tertiaryContainer, width = 1.dp
            ).padding(4.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.ExtraBold
        )
    }
}