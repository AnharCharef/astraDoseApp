package com.anhar.atcadaptor.domain.model.details

import com.anhar.atcadaptor.domain.model.calculations.CalculationsHistory
import kotlinx.serialization.Serializable

@Serializable
data class PatientData(
    val age: String="",
    val genre: String="",
    val poids: String="",
    val race: String="",
    val taille: String="",
    val userEmail: String="",
    val patientId: Int=0,
    val userId: Int=0,
    val userName: String="",
    val userPhone: String="",
    val calculations : List<CalculationsHistory> = emptyList()
)