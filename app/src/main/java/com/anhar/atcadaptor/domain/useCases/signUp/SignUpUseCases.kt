package com.anhar.atcadaptor.domain.useCases.signUp

import com.anhar.atcadaptor.domain.useCases.auth.CheckEmailUseCase
import com.anhar.atcadaptor.domain.useCases.auth.CheckPasswordUseCase

data class SignUpUseCases (
    val checkEmail : CheckEmailUseCase,
    val checkPassword: CheckPasswordUseCase,
    val checkUserName: CheckUserNameUseCase,
    val checkPhone : CheckPhoneUseCase,
    val addUser : AddUserUseCase,
    val verifyCode : VerifyCodeUseCase ,
)