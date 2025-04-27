package com.anhar.atcadaptor.presentation.mainApp.calculation

import com.anhar.atcadaptor.common.DFGType
import com.anhar.atcadaptor.common.Genre
import com.anhar.atcadaptor.common.Race

sealed class CalculationInputEvent {
    data class OnNomDeMedicamentChanged(val value: String) : CalculationInputEvent()
    data class OnAgeChanged(val value: String) : CalculationInputEvent()
    data class OnPoidsChanged(val value: String) : CalculationInputEvent()
    data class OnTailleChanged(val value: String) : CalculationInputEvent()
    data class OnGenreChanged(val value: Genre) : CalculationInputEvent()
    data class OnRaceChanged(val value: Race) : CalculationInputEvent()
    data class OnCreatinineChanged(val value: String) : CalculationInputEvent()
    data class OnDfgChanged(val value: String) : CalculationInputEvent()
    data class OnDfgTypeChanged(val value: DFGType) : CalculationInputEvent()
    data class OnAlatChanged(val value: String) : CalculationInputEvent()
    data class OnAsatChanged(val value: String) : CalculationInputEvent()
    data class OnPalChanged(val value: String) : CalculationInputEvent()
    data class OnTbilChanged(val value: String) : CalculationInputEvent()
    data class OnDoseParM2Changed(val value: String) : CalculationInputEvent()
    data class OnAucCibleChanged(val value: String) : CalculationInputEvent()
    data class OnToxiciteRenaleChanged(val value: Boolean) : CalculationInputEvent()
    data class OnToxiciteHepatiqueChanged(val value: Boolean) : CalculationInputEvent()
    data class OnNecessiteDialyseChanged(val value: Boolean) : CalculationInputEvent()
    data class OnScChanged(val value: String) : CalculationInputEvent()
    data class OnDfgCalculeChanged(val value: String) : CalculationInputEvent()
    data class OnDoseCarboplatineChanged(val value: String) : CalculationInputEvent()


}