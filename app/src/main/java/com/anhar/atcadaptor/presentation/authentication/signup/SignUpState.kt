package com.anhar.atcadaptor.presentation.authentication.signup

import java.io.Serializable

data class SignUpState(
    val isLoading : Boolean = false,
    val userName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val userNameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val verificationCode: String = "",
    val signUpSuccessful: Boolean = false,
    val signUpErrorMessage : String?=null
) : Serializable
