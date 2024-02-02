package com.pscode.app.presentation.composables

import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
actual fun YoutubeView(videoId: String, modifier: Modifier) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    lateinit var youtubePlayerView: YouTubePlayerView

    DisposableEffect(Unit) {
        onDispose {
            youtubePlayerView.release()
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    this.enableAutomaticInitialization = false
                    youtubePlayerView = this
                    val youtubePlayerListener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            val defaultPlayerUiController =
                                DefaultPlayerUiController(youtubePlayerView, youTubePlayer)
                            defaultPlayerUiController.showFullscreenButton(false)
                            youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                            youTubePlayer.loadOrCueVideo(
                                lifecycle = lifecycle, videoId = videoId, startSeconds = 0f
                            )
                        }
                    }
                    val iFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()

                    this.initialize(youtubePlayerListener, iFramePlayerOptions)
                }
            })
    }
}