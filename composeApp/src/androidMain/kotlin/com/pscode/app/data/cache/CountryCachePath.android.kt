package com.pscode.app.data.cache

lateinit var homeFilePath: String
actual fun getCountryCachePath(): CountryCachePath = AndroidCountryCachePath()
class AndroidCountryCachePath : CountryCachePath {
    override val name: String = homeFilePath
}