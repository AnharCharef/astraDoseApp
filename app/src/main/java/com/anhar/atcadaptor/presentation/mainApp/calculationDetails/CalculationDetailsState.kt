package com.anhar.atcadaptor.presentation.mainApp.calculationDetails

import com.anhar.atcadaptor.domain.model.calculations.CalculationData

data class CalculationDetailsState(
    val isLoading : Boolean = false ,
    val error : String?= null ,
    val calculationData: CalculationData? = null
)
