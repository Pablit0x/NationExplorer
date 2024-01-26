package com.pscode.app.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.scalesLargeContentImage
import platform.WebKit.WKWebView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun YoutubeView(videoId: String, modifier: Modifier) {

    val youtubeUrl = NSURL.URLWithString("https://www.youtube.com/embed/$videoId")

    val webView by remember {
        mutableStateOf(WKWebView().apply {
            scrollView.scrollEnabled = false
            scalesLargeContentImage = true
            loadRequest(NSURLRequest(youtubeUrl ?: return@apply))
        })
    }

    UIKitView(modifier = modifier, factory = { webView })
}