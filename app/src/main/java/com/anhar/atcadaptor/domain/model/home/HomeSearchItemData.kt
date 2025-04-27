package com.anhar.atcadaptor.domain.model.home

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class HomeSearchItemData(
    val auc: String?,
    val dfg: String?,
    val dose: String?,
    val medicationName: String?,
    val patientId: Int ?= null,
    val patientName: String?= null,
    val toxicity: String?
)