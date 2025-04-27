package com.anhar.atcadaptor.presentation.mainApp.calculation

import com.anhar.atcadaptor.domain.model.medicament.Medicament
import com.anhar.atcadaptor.domain.model.user.User

data class CalculationState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val showBottomSheet: Boolean = false,

    val dropGenreMenu: Boolean = false,
    val dropRaceMenu: Boolean = false,
    val dropToxiciteRenaleMenu: Boolean = false,
    val dropToxiciteHepatiqueMenu: Boolean = false,
    val dropDialyseMenu: Boolean = false,
    val dropDfgTypeMenu: Boolean = false,
    val dropNomDeMedicamentMenu: Boolean = false,

    val recommendationAndDose : Pair<String,Double> = Pair("" , 0.0),
    val medicamentsList : List<Medicament> = emptyList(),

    val comment : String ="",

    val showUserSelection: Boolean = false,
    val selectedUser: User? = null,
    val userSearchQuery: String = "",
    val userSearchResults: List<User> = emptyList()
)
