package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun DetailCountryInformationItem(
    icon: ImageVector,
    iconDescription: String,
    key: String,
    value: String,
    modifier: Modifier = Modifier,
    keyAppendix: String = ": ",
    valueAppendix: String? = null
) {

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(key)
            append(keyAppendix)
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold
            )
        ) {
            append(value)
            valueAppendix?.let {
                append(it)
            }
        }
    }


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        Text(text = annotatedString)
    }
}