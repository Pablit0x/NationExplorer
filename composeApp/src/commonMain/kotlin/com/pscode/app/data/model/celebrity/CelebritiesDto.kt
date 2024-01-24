package com.pscode.app.data.model.celebrity


import com.pscode.app.domain.model.CelebrityData
import com.pscode.app.domain.model.CelebrityInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CelebritiesDto(
    @SerialName("celebrities") val celebrities: List<Celebrity>,
    @SerialName("country") val country: String
)

fun CelebritiesDto.toCelebrityData(): CelebrityData {
    return CelebrityData(data = celebrities.map { celebrity ->
        CelebrityInfo(
            id = celebrity.id,
            countryName = country,
            name = celebrity.name,
            description = celebrity.description,
            imageUrl = celebrity.image
        )
    })
}