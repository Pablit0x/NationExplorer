package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun YoutubeView(videoId: String, modifier : Modifier = Modifier)