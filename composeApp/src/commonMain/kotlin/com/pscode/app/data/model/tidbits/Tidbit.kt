package com.pscode.app.data.model.tidbits


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tidbit(
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String
)