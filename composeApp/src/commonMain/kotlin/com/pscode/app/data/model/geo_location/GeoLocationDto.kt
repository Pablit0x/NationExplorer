package com.pscode.app.data.model.geo_location

import com.pscode.app.domain.model.GeoLocationOverview
import kotlinx.serialization.Serializable

@Serializable
data class GeoLocationDto(
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
)

fun GeoLocationDto.toGeoLocationOverview() : GeoLocationOverview {
    return GeoLocationOverview(
        cityName = this.name,
        countryName = this.country,
        lat = this.latitude,
        long = this.longitude
    )
}