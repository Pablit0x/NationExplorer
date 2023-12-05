package com.pscode.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Flags(
    val png: String,
    val svg: String
)
