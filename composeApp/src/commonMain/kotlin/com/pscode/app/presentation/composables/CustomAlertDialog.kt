package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes


@Composable
fun CustomAlertDialog(
    title: String,
    message: String,
    isOpen: Boolean,
    onEndClicked: () -> Unit,
    onRestartClicked: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
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
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
        )

    }
}

