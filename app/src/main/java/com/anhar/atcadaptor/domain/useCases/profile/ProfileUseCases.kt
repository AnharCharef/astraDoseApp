package com.anhar.atcadaptor.domain.useCases.profile


import com.anhar.atcadaptor.domain.useCases.auth.CheckEmailUseCase
import com.anhar.atcadaptor.domain.useCases.auth.CheckPasswordUseCase
import com.anhar.atcadaptor.domain.useCases.signUp.CheckPhoneUseCase
import com.anhar.atcadaptor.domain.useCases.signUp.CheckUserNameUseCase


data class ProfileUseCases (
    val saveUserData : SaveUserData,
    val getUserData : GetUserDataUseCase,
    val updatePersonalDetails : UpdatePersonalDetailsUseCase,
    val checkEmail : CheckEmailUseCase,
    val checkPassword: CheckPasswordUseCase,
    val checkUserName: CheckUserNameUseCase,
    val checkPhone : CheckPhoneUseCase,
    val checkEmailAvailability : CheckEmailAvailabilityUseCase ,
    val updateUserQrCode : UpdateUserQrCodeUseCase
)