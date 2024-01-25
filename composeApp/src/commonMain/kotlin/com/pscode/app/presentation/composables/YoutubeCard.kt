package com.pscode.app.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pscode.app.SharedRes
import com.pscode.app.presentation.screens.countries.detail.states.CardState
import com.pscode.app.presentation.screens.countries.detail.states.YoutubeVideoState

@Composable
fun YoutubeCard(
    youtubeVideoState: YoutubeVideoState, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    youtubeVideoState.youtubeVideoData?.let { youtubeVideoData ->
        ElevatedCard(modifier = modifier, shape = RoundedCornerShape(10)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().noRippleClickable(onClick)
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Movie,
                            contentDescription = "Youtube Video about that country"
                        )
                        Text(
                            text = SharedRes.string.video,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    if (youtubeVideoState.cardState == CardState.EXPENDED) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp, "Close Video")
                    } else {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, "Show Video")
                    }

                }

                if (youtubeVideoState.cardState == CardState.EXPENDED) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp).padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        YoutubeView(videoId = youtubeVideoData.videoId, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}