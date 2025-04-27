package com.anhar.atcadaptor.data.remote.details.dto

import com.anhar.atcadaptor.domain.model.details.DetailsData
import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    val data: DetailsData?=null,
    val message: String,
    val status: String
)