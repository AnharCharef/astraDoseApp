package com.anhar.atcadaptor.data.remote.details.dto

import com.anhar.atcadaptor.domain.model.details.PatientData
import kotlinx.serialization.Serializable

@Serializable
data class PatientDataResponse(
    val message: String,
    val patientData: PatientData?=null,
    val status: String
)