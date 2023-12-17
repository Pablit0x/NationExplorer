package com.pscode.app.data.cache

import okio.Path
import okio.Path.Companion.toPath

lateinit var geolocationCachePath: String

actual fun getGeolocationCachePath(): GeolocationCachePath = AndroidGeolocationCachePath()

class AndroidGeolocationCachePath : GeolocationCachePath {
    override val path: Path = geolocationCachePath.toPath()
}
