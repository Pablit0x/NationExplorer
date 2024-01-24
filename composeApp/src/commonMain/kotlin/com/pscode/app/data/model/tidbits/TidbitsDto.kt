package com.pscode.app.data.model.tidbits


import com.pscode.app.domain.model.Tidbit
import com.pscode.app.domain.model.TidbitData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TidbitsDto(
    @SerialName("name") val name: String, @SerialName("tidbits") val tidbits: List<TidbitDto>
)

fun TidbitsDto.toTidbitData(): TidbitData {
    return TidbitData(data = this.tidbits.map { tidbit ->
        Tidbit(
            countryName = name,
            id = tidbit.id,
            title = tidbit.title,
            description = tidbit.description
        )
    })
}
