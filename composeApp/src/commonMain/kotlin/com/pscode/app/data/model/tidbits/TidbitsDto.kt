package com.pscode.app.data.model.tidbits


import com.pscode.app.domain.model.TidbitData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TidbitsDto(
    @SerialName("name")
    val name: String,
    @SerialName("tidbits")
    val tidbits: List<Tidbit>
)

fun TidbitsDto.toListOfTidbitsOverview(): List<TidbitData> {
    return tidbits.map { tidbit ->
        TidbitData(
            countryName = this.name,
            id = tidbit.id,
            title = tidbit.title,
            description = tidbit.description
        )
    }
}
