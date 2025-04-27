package com.anhar.atcadaptor.presentation.mainApp.calculationsHistory

import com.anhar.atcadaptor.domain.model.calculations.CalculationsHistory

data class CalculationsHistoryState(
    val isLoading : Boolean = false ,
    val error : String ? = null ,
    val historyList : List<CalculationsHistory> = emptyList()
)
