package com.pscode.app.data.cache

import okio.Path

interface CountryCachePath {
    val path: Path
}

expect fun getCountryCachePath(): CountryCachePath