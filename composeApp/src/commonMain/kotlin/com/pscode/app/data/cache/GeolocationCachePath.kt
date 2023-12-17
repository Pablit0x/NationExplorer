package com.pscode.app.data.cache

import okio.Path

interface GeolocationCachePath {
    val path: Path
}

expect fun getGeolocationCachePath(): GeolocationCachePath