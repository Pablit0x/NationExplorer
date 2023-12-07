package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.statusBarsPadding().fillMaxWidth().height(56.dp),
    ) {

        OutlinedTextField(
            value = text,
            onValueChange = { onTextChange(it) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClicked()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            },
            placeholder = {
                Text(text = SharedRes.string.search)
            },
            shape = RoundedCornerShape(percent = 30),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
    }
}
