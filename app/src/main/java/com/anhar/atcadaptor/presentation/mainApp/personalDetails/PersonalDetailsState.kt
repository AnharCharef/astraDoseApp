package com.anhar.atcadaptor.presentation.mainApp.personalDetails

import com.anhar.atcadaptor.domain.model.user.User

data class PersonalDetailsState(
    val isLoading :Boolean = false,
    val user : User = User(),
    val editPassword: Boolean = false,
    val newPassword : String = "",
    val oldPassword : String = "",
    val showPassword: Boolean = false,
    val showNewPassword: Boolean = false,
    val userNameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val oldPasswordError: String? = null,
    val newPasswordError: String? = null,
    val updateSuccess : Boolean = false,
    val updateError : String ?= null,
    val showDialog: Boolean = false,
    val emailErrorDialog : Boolean = false,
    val emailVerified : Boolean = false ,
    val emailAvailable : Boolean = false
)
