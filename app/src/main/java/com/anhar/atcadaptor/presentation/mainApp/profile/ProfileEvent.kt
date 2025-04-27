package com.anhar.atcadaptor.presentation.mainApp.profile

import android.content.Context

sealed class ProfileEvent {

    data class SetUserId(val userId: Int) : ProfileEvent()
    data object Logout : ProfileEvent()
    data object ShowDialog : ProfileEvent()
    data object HideDialog : ProfileEvent()
    data class ToggleNotification(val value : Boolean) : ProfileEvent()
    data class GetUserData(val context : Context) : ProfileEvent()
    data object UpdateUserQrCode : ProfileEvent()

}