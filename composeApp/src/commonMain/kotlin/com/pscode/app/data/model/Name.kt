package com.pscode.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val common: String,
    val official: String
)
