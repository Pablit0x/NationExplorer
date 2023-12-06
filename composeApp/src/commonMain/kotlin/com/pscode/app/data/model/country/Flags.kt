package com.pscode.app.data.model.country

import kotlinx.serialization.Serializable

@Serializable
data class Flags(
    val png: String,
    val svg: String
)
