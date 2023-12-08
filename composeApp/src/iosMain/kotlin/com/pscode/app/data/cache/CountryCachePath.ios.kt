package com.pscode.app.data.cache

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.autoreleasepool
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUserDomainMask

actual fun getCountryCachePath(): CountryCachePath = IOSCountryCachePath()

class IOSCountryCachePath : CountryCachePath {
    @OptIn(BetaInteropApi::class)
    override val path: Path by lazy {
        autoreleasepool {
            val paths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true)
            val cacheDirectory = paths.first() as NSString
            "${cacheDirectory}/country_cache.json".toPath()
        }
} }
