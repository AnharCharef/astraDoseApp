package com.anhar.atcadaptor.domain.model.details

import kotlinx.serialization.Serializable

@Serializable
data class DetailsData(
    val age: Int?= null,
    val alat: String?= null,
    val asat: String?= null,
    val auc: String?= null,
    val creatinine: String?= null,
    val dfg: String?= null,
    val dialysisRequired: Int?= null,
    val dose: String?= null,
    val genre: String?= null,
    val medicationName: String?= null,
    val name: String?= null,
    val pal: String?= null,
    val patientId: Int?= null,
    val poids: String?= null,
    val race: String?= null,
    val taille: String?= null,
    val tbil: String?= null,
    val toxicityHepatic: String?= null,
    val toxicityRenal: String?= null
)