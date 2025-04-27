package com.anhar.atcadaptor.data.remote.home.dto

import com.anhar.atcadaptor.domain.model.home.HomeSearchItemData
import kotlinx.serialization.Serializable

@Serializable
data class HomeSearchResponse(
    val data: List<HomeSearchItemData>? = null,
    val message: String,
    val status: String
)