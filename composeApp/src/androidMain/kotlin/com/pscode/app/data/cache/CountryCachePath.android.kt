package com.pscode.app.data.cache

import okio.Path
import okio.Path.Companion.toPath

lateinit var countryCacheFilePath: String
actual fun getCountryCachePath(): CountryCachePath = AndroidCountryCachePath()
class AndroidCountryCachePath : CountryCachePath {
    override val path: Path = countryCacheFilePath.toPath()
}