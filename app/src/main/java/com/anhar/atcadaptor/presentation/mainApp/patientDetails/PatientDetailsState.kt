package com.anhar.atcadaptor.presentation.mainApp.patientDetails

import com.anhar.atcadaptor.domain.model.details.PatientData

data class PatientDetailsState(
    val isLoading : Boolean = false ,
    val error : String ? = null ,
    val patientData: PatientData? = null ,
)
