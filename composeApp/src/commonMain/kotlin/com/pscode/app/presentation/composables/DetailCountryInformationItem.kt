package com.pscode.app.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailCountryInformationItem(
    icon: ImageVector,
    iconDescription: String,
    key: String,
    value: String,
    modifier: Modifier = Modifier,
    keyAppendix: String = ": ",
    valueAppendix: String? = null,
    marqueeEffect: Boolean = false,
) {

    val annotatedKey = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(key)
            append(keyAppendix)
        }
    }

    val annotatedValue = buildAnnotatedString {
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
        modifier = modifier
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = annotatedKey)
        Text(
            text = annotatedValue, modifier = Modifier.then(
                if (marqueeEffect) {
                    Modifier.basicMarquee()
                } else {
                    Modifier
                }
            )
        )
    }
}