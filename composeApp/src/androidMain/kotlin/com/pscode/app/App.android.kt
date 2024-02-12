package com.pscode.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.pscode.app.data.cache.countryCacheFilePath
import com.pscode.app.data.cache.geolocationCachePath
import com.pscode.app.utils.WindowSize

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugBuild()
        countryCacheFilePath = "${filesDir.path}/country_cache.json"
        geolocationCachePath = "${filesDir.path}/geolocation_cache.json"
        setContent {
            val windowSize = rememberWindowSize()

            App(windowSize = windowSize)
        }
    }
}

@Composable
private fun Activity.rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    return WindowSize.basedOnWidth(configuration.screenWidthDp.dp)
}