package com.pscode.app.data.cache

import okio.Path
import okio.Path.Companion.toPath

lateinit var homeFilePath: String
actual fun getCountryCachePath(): CountryCachePath = AndroidCountryCachePath()
class AndroidCountryCachePath : CountryCachePath {
    override val path: Path = homeFilePath.toPath()
}