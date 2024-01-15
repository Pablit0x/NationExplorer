package com.pscode.app.data.model.celebrity


import com.pscode.app.domain.model.CelebrityOverview
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CelebrityDto(
    @SerialName("celebrities") val celebrities: List<Celebrity>,
    @SerialName("country") val country: String
)

fun CelebrityDto.toListOfCelebrityOverview(): List<CelebrityOverview> {
    return celebrities.map { celebrity ->
        CelebrityOverview(
            id = celebrity.id,
            countryName = country,
            name = celebrity.name,
            description = celebrity.description,
            imageUrl = celebrity.image
        )
    }
}