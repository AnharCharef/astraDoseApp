package com.anhar.atcadaptor.presentation.authentication.forgotPassword

import java.io.Serializable

data class ForgotPasswordState(
    val isLoading : Boolean = false,
    val email : String = "",
    val verificationCode : String = "",
    val newPassword : String= "",
    val showPassword : Boolean = false,
    val emailError : String? =null,
    val verificationCodeError : Boolean? = null,
    val verificationErrorMessage : String ?=null,
    val passwordError : String? = "",
    val resetPasswordSuccessful : Boolean = false,
    val checkEmailSuccessful : Boolean = false,
    val checkEmailError: String? = null,
    val resetPasswordError : String ? = null
) : Serializable
