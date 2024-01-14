package com.pscode.app.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.utils.Constants


@Composable
fun GameResultsDialog(
    userScore: Int,
    currentStopWatchTime: String,
    oldPersonalBest: Pair<Int, String>,
    hasNewPersonalBest: Boolean,
    isOpen: Boolean,
    onEndClicked: () -> Unit,
    onRestartClicked: () -> Unit,
    navigateToLeaderboard: () -> Unit
) {
    if (isOpen) {
        AlertDialog(confirmButton = {
            FilledTonalButton(onClick = {
                onRestartClicked()
            }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Restart game")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = SharedRes.string.restart)
            }
        }, dismissButton = {
            TextButton(onClick = {
                onEndClicked()
            }) {
                Text(text = SharedRes.string.end_game)
            }
        }, onDismissRequest = onEndClicked, text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {

                TextButton(onClick = navigateToLeaderboard, modifier = Modifier.fillMaxWidth()) {
                    Text(text = SharedRes.string.view_leaderboard)
                }

                Row(
                    modifier = Modifier.fillMaxWidth().background(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.outline,
                                MaterialTheme.colorScheme.outlineVariant
                            )
                        ), shape = RoundedCornerShape(percent = 15), alpha = 0.2f
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = SharedRes.string.score,
                        modifier = Modifier.padding(8.dp),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$userScore/${Constants.NUMBER_OF_ROUNDS}",
                        modifier = Modifier.padding(8.dp),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().background(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.outline,
                                MaterialTheme.colorScheme.outlineVariant
                            )
                        ), shape = RoundedCornerShape(percent = 15), alpha = 0.2f
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = SharedRes.string.time,
                        modifier = Modifier.padding(8.dp),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = currentStopWatchTime,
                        modifier = Modifier.padding(8.dp),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }

                AutoResizedText(
                    text = if (hasNewPersonalBest) {
                        SharedRes.string.new_personal_best
                    } else {
                        SharedRes.string.old_personal_best.format(oldPb = "(${oldPersonalBest.first}/${Constants.NUMBER_OF_ROUNDS}, ${oldPersonalBest.second})")
                    },
                    textAlign = TextAlign.Center,
                    color = if (hasNewPersonalBest) Color.Yellow else MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        })

    }
}

