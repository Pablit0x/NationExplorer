package com.pscode.app.data.cache

import okio.Path
import okio.Path.Companion.toPath

lateinit var geoLocationCachePath: String

actual fun getGeoLocationCachePath(): GeoLocationCachePath = AndroidGeoLocationCachePath()

class AndroidGeoLocationCachePath : GeoLocationCachePath {
    override val path: Path = geoLocationCachePath.toPath()
}
