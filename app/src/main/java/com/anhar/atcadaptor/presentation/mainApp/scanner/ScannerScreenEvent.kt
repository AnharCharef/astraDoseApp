package com.anhar.atcadaptor.presentation.mainApp.scanner

sealed class ScannerScreenEvent {
    data class SaveAppEntry(val value: String) : ScannerScreenEvent()

    data class GetPatientData(val name: String, val email: String, val phone: String) :
        ScannerScreenEvent()

    data object ToggleNeedToBeLoggedBottomSheet : ScannerScreenEvent()
}