package com.pscode.app.data.model.celebrity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Celebrity(
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String
)