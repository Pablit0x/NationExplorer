package com.pscode.app

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pscode.app.data.cache.countryCacheFilePath
import com.pscode.app.data.cache.geoLocationCachePath

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
        countryCacheFilePath = "${filesDir.path}/country_cache.json"
        geoLocationCachePath = "${filesDir.path}/geolocation_cache.json"
        setContent {
            App()
        }
    }
}