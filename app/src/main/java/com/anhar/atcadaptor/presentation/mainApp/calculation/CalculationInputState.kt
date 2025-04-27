package com.anhar.atcadaptor.presentation.mainApp.calculation

import com.anhar.atcadaptor.common.DFGType
import com.anhar.atcadaptor.common.Genre
import com.anhar.atcadaptor.common.Race

data class CalculationInputState(
    val nomDeMedicament: String = "",
    val nomDeMedicamentError: String? = null,

    val age: String = "0",
    val ageError: String? = null,

    val poids: String = "0",
    val poidsError: String? = null,

    val taille: String = "0",
    val tailleError: String? = null,

    val genre: Genre = Genre.Femme,


    val race: Race = Race.Non_Afro_Americain,


    val creatinine: String = "0",
    val creatinineError: String? = null,

    val dfg: String = "0",
    val dfgError: String? = null,

    val dfgType: DFGType = DFGType.MDRD,

    val alat: String = "0",
    val alatError: String? = null,

    val asat: String = "0",
    val asatError: String? = null,

    val pal: String = "0",
    val palError: String? = null,

    val tbil: String = "0",
    val tbilError: String? = null,

    val doseParM2: String = "0",
    val doseParM2Error: String? = null,

    val aucCible: String = "0",
    val aucCibleError: String? = null,

    val toxiciteRenale: Boolean = false,


    val toxiciteHepatique: Boolean = false,


    val necessiteDialyse: Boolean = false,


    val sc: String = "",
    val scError: String? = null,

    val dfgCalcule: String = "",
    val dfgCalculeError: String? = null,

    val doseCarboplatine: String = "",
    val doseCarboplatineError: String? = null
)
