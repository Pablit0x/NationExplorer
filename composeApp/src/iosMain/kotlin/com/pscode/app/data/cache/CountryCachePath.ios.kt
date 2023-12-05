package com.pscode.app.data.cache

import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory

actual fun getCountryCachePath(): CountryCachePath = IOSCountryCachePath()

class IOSCountryCachePath : CountryCachePath {
    override val path: Path = "${NSHomeDirectory()}/country_cache.json".toPath()
}