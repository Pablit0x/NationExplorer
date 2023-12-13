package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes


@Composable
fun UsernameInputDialog(
    isOpen: Boolean, onNextClicked: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }

    if (isOpen) {
        AlertDialog(title = {
            Text(
                text = SharedRes.string.enter_username,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }, confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                FilledTonalButton(onClick = {
                    onNextClicked(username)
                }) {
                    Text(text = SharedRes.string.next)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "Next")
                }
            }
        }, onDismissRequest = {}, text = {
                OutlinedTextField(shape = RoundedCornerShape(percent = 10),
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    value = username,
                    onValueChange = { username = it },
                    placeholder = {
                        Text(text = SharedRes.string.username_placeholder)
                    })
        })

    }
}
