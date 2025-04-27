package com.anhar.atcadaptor.domain.model.medicament

import kotlinx.serialization.Serializable

@Serializable
data class Medicament(
    val medicaments_auc_cible: String,
    val medicaments_id: Int,
    val medicaments_nom: String
)