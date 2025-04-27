package com.anhar.atcadaptor.domain.model.calculations

import kotlinx.serialization.Serializable


@Serializable
data class CalculationData(
    val calculation_id: Int? = null,
    val user_id: Int,
    val calculated_by: Int,
    val nom_de_medicament: String,
    val age: Int,
    val poids: String,
    val taille: String,
    val genre: String,
    val race: String,
    val creatinine: String,
    val alat: String,
    val asat: String,
    val pal: String,
    val tbil: String,
    val dfg_type: String,
    val dfg: String,
    val dfg_calcule: String,
    val auc_cible: String,
    val dose_carboplatine: String,
    val dose_par_m2: String,
    val toxicite_renale: Int,
    val toxicite_hepatique: Int,
    val necessite_dialyse: Int,
    val sc: String,
    val recommandation: String,
    val dose_recommandee: String,
    val comment: String,
    val created_at: String? = null
)