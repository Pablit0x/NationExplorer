package com.pscode.app.data.cache

import com.pscode.app.domain.model.LocationOverview
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf

class GeolocationCache {
    val cache: KStore<List<LocationOverview>> = storeOf(file = getGeolocationCachePath().path)
}