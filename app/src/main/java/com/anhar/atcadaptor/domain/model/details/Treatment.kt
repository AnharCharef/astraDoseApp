package com.anhar.atcadaptor.domain.model.details

data class Treatment(
    val medicationName: String,
    val dose: Double,
    val auc: Double,
    val toxicityRenal: String,
    val toxicityHepatic: String,
    val dialysisRequired: Boolean,
    val creatinine: Double,
    val dfg: Double,
    val alat: Double,
    val asat: Double,
    val pal: Double,
    val tbil: Double
)


