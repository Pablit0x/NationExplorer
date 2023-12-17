package com.pscode.app.data.cache

import okio.Path

interface GeoLocationCachePath {
    val path: Path
}

expect fun getGeoLocationCachePath(): GeoLocationCachePath