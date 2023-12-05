package com.pscode.app.data.cache

import platform.Foundation.NSHomeDirectory

actual fun getCountryCachePath(): CountryCachePath = IOSCountryCachePath()

class IOSCountryCachePath : CountryCachePath {
    override val name: String = "${NSHomeDirectory()}/country_cache.json"
}