package com.pscode.app.data.model.geolocation


import com.pscode.app.domain.model.LocationData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status")
    val status: Status,
    @SerialName("total_results")
    val totalResults: Int
)

fun LocationDto.toLocationOverview(countryName: String): LocationData {
    return LocationData(
        name = countryName,
        latitude = results.first().geometry.lat,
        longitude = results.first().geometry.lng
    )
}