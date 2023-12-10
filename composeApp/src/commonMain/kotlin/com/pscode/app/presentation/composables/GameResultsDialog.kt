package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes


@Composable
fun GameResultsDialog(
    score: String,
    time: String,
    pbMessage: String,
    newBest: Boolean,
    isOpen: Boolean,
    onEndClicked: () -> Unit,
    onRestartClicked: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            title = {
                Text(
                    text = SharedRes.string.results,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(onClick = {
                    onRestartClicked()
                }) {
                    Text(text = SharedRes.string.restart)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Restart game")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onEndClicked()
                }) {
                    Text(text = SharedRes.string.end_game)
                }
            },
            onDismissRequest = onEndClicked,
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {

                    Text(
                        text = score,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = time,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Medium
                    )

                    AutoResizedText(
                        text = pbMessage,
                        textAlign = if (newBest) TextAlign.Center else TextAlign.Start,
                        color = if (newBest) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = if (newBest) 8.dp else 0.dp)
                    )
                }
            },
        )

    }
}

