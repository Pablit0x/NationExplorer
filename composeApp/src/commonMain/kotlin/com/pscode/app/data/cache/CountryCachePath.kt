package com.pscode.app.data.cache

interface CountryCachePath {
    val name: String
}

expect fun getCountryCachePath(): CountryCachePath