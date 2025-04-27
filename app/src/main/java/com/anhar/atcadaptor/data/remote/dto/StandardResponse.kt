package com.anhar.atcadaptor.data.remote.dto
import kotlinx.serialization.Serializable


@Serializable
data class StandardResponse(
    val status: String,
    val message: String,
)