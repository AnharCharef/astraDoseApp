package com.anhar.atcadaptor.presentation.mainApp.personalDetails

import android.content.Context
import com.anhar.atcadaptor.domain.model.user.User

sealed class PersonalDetailsEvent {

    data class UpdateUserName(val name: String) : PersonalDetailsEvent()
    data class UpdateEmail(val email: String) : PersonalDetailsEvent()
    data class UpdatePhone(val phone: String) : PersonalDetailsEvent()
    data class UpdatePassword(val password: String) : PersonalDetailsEvent()
    data class UpdateNewPassword(val newPassword: String) : PersonalDetailsEvent()
    data object ToggleShowPassword : PersonalDetailsEvent()
    data object ToggleShowNewPassword : PersonalDetailsEvent()
    data object ToggleEditPassword : PersonalDetailsEvent()
    data object ToggleShowDialog : PersonalDetailsEvent()
    data object ToggleShowEmailDialog : PersonalDetailsEvent()
    data object ToggleUpdatedSuccessState : PersonalDetailsEvent()
    data class SaveUserInformation(val user: User) : PersonalDetailsEvent()
    data class UpdatePersonalDetails(
        val context : Context,
        val initialData : User,
        val userId: Int,
        val name: String,
        val email: String,
        val phone: String,
        val oldPassword: String,
        val newPassword: String,
    ) : PersonalDetailsEvent()

    data class CheckEmailAvailability(
        val context: Context ,
        val userId : Int ,
        val email :String
    ) : PersonalDetailsEvent()

}