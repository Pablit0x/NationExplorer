package com.pscode.app.presentation.screens.countries.flag_game.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.domain.model.Result
import com.pscode.app.presentation.composables.AutoResizedText
import com.pscode.app.utils.Constants.NUMBER_OF_ROUNDS
import io.realm.kotlin.mongodb.User

@Composable
fun LeaderboardListItem(
    rank: Int, result: Result, currentUser: User?, modifier: Modifier = Modifier
) {
    val isCurrentUser by remember { mutableStateOf( result.userId == currentUser?.id) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        Text(
            text = "$rank",
            modifier = Modifier.weight(0.5f)
                .border(color = MaterialTheme.colorScheme.outlineVariant, width = 1.dp)
                .padding(4.dp)
                .then(
                    if (isCurrentUser) {
                        Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)
                    } else Modifier
                ),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            color = if(isCurrentUser) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = result.username,
            modifier = Modifier.weight(1f)
                .border(color = MaterialTheme.colorScheme.outlineVariant, width = 1.dp)
                .padding(4.dp)
                .then(
                    if (isCurrentUser) {
                        Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)
                    } else Modifier
                ),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = if(isCurrentUser) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground
        )

        AutoResizedText(
            text = "${result.score}/$NUMBER_OF_ROUNDS",
            modifier = Modifier.weight(1f)
                .border(color = MaterialTheme.colorScheme.outlineVariant, width = 1.dp)
                .padding(4.dp)
                .then(
                    if (isCurrentUser) {
                        Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)
                    } else Modifier
                ),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = if(isCurrentUser) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground
        )

        AutoResizedText(
            text = result.time,
            modifier = Modifier.weight(1f)
                .border(color = MaterialTheme.colorScheme.outlineVariant, width = 1.dp)
                .padding(4.dp)
                .then(
                    if (isCurrentUser) {
                        Modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)
                    } else Modifier
                ),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = if(isCurrentUser) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground

        )
    }
}