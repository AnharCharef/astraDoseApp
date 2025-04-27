package com.anhar.atcadaptor.presentation.mainApp.details

sealed class DetailsEvent {

    data class GetDetails (val medicationName : String , val patientId : Int? = null) : DetailsEvent()

}