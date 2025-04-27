package com.anhar.atcadaptor.data.remote.calculation.dto

import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import kotlinx.serialization.Serializable

@Serializable
data class GetCalculationByIdResponse(
    val data: CalculationData?=null,
    val message: String,
    val status: String
)