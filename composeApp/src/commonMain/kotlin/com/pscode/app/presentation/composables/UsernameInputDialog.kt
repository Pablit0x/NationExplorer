package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameInputDialog(
    isOpen: Boolean, onNextClicked: (String) -> Unit
) {
    val tooltipState = remember { PlainTooltipState() }
    val scope = rememberCoroutineScope()
    var username by remember { mutableStateOf("") }

    if (isOpen) {
        AlertDialog(title = {
            Text(
                text = SharedRes.string.enter_username,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
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
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                value = username,
                onValueChange = { username = it },
                placeholder = {
                    Text(text = SharedRes.string.username_placeholder)
                },
                trailingIcon = {
                    if (username.isEmpty()) {
                        PlainTooltipBox(containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(percent = 10),
                            tooltipState = tooltipState,
                            tooltip = {
                                Text(
                                    SharedRes.string.username_tooltip,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }) {
                            IconButton(onClick = {
                                scope.launch {
                                    tooltipState.show()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "Show Username Tooltip"
                                )
                            }
                        }
                    }
                })

        })


    }
}
