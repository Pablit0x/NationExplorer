package com.pscode.app.data.cache

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.autoreleasepool
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUserDomainMask

actual fun getGeolocationCachePath(): GeolocationCachePath = IOSGeolocationCachePath()

class IOSGeolocationCachePath : GeolocationCachePath {
    @OptIn(BetaInteropApi::class)
    override val path: Path by lazy {
        autoreleasepool {
            val paths =
                NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true)
            val cacheDirectory = paths.first() as NSString
            "${cacheDirectory}/geolocation_cache.json".toPath()
        }
    }
}