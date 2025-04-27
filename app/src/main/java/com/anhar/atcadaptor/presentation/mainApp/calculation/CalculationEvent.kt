package com.anhar.atcadaptor.presentation.mainApp.calculation

import android.content.Context
import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import com.anhar.atcadaptor.domain.model.user.User

sealed class CalculationEvent {
    data class OnCalculate(val context: Context) : CalculationEvent()
    data object InitializeErrorMessage : CalculationEvent()
    data object ToggleGenreDropMenu : CalculationEvent()
    data object ToggleRaceDropMenu : CalculationEvent()
    data object ToggleToxiciteRenaleDropMenu : CalculationEvent()
    data object ToggleToxiciteHepatiqueDropMenu : CalculationEvent()
    data object ToggleDialyseDropMenu : CalculationEvent()
    data object ToggleDfgTypeDropMenu : CalculationEvent()
    data object ToggleShowBottomSheet : CalculationEvent()
    data object ToggleNomDeMedicament : CalculationEvent()

    data object GetMedicament : CalculationEvent()

    data class  OnCommentValueChange (val value : String) : CalculationEvent()
    data class  SaveCalculation (val context : Context , val calculationData: CalculationData) : CalculationEvent()
    data class  ShowToast (val context: Context,val message: String ) : CalculationEvent()


    data object ToggleShowUserSelection : CalculationEvent()
    data class OnUserSearchQueryChange(val query: String) : CalculationEvent()
    data class OnSearch(val query: String) : CalculationEvent()
    data class OnUserSelected(val user: User) : CalculationEvent()

}