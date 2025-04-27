package com.anhar.atcadaptor.presentation.authentication.signup

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Constant.bitmapToBase64
import com.anhar.atcadaptor.common.Constant.generateQRCode
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.signUp.SignUpUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class SignUpViewModel @Inject constructor(
    private val signUpUseCases: SignUpUseCases,
) : ScreenModel {
    private var _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState

    fun setState(newState: SignUpState) {
        _signUpState.value = newState
    }


    fun updateEmail(value: String) {
        _signUpState.value = signUpState.value.copy(
            email = value,
        )
    }

    fun updatePassword(value: String) {
        _signUpState.value = signUpState.value.copy(
            password = value,
        )
    }

    fun updatePhone(value: String) {
        _signUpState.value = signUpState.value.copy(
            phone = value,
        )
    }

    fun updateUserName(value: String) {
        _signUpState.value = signUpState.value.copy(
            userName = value,
        )
    }

    fun showOrHidePassword(showPassword: Boolean) {
        _signUpState.value = signUpState.value.copy(
            showPassword = showPassword,
        )
    }

    fun updateVerificationCode(value: String) {
        _signUpState.value = signUpState.value.copy(
            verificationCode = value
        )
    }


    private fun validateEmail(email: String, context: Context): Boolean {
        when (val result = signUpUseCases.checkEmail(email, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _signUpState.value = signUpState.value.copy(
                    emailError = result.message,
                )
            }

            is Resource.Successful -> {
                _signUpState.value = signUpState.value.copy(
                    emailError = null
                )

            }
        }
        return signUpState.value.emailError.isNullOrEmpty()
    }

    private fun validatePassword(password: String, context: Context): Boolean {
        when (val result = signUpUseCases.checkPassword(password, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _signUpState.value = signUpState.value.copy(
                    passwordError = result.message,
                )
            }

            is Resource.Successful -> {
                _signUpState.value = signUpState.value.copy(
                    passwordError = null
                )

            }
        }
        return signUpState.value.passwordError.isNullOrEmpty()
    }


    private fun validateUserName(userName: String, context: Context): Boolean {
        when (val result = signUpUseCases.checkUserName(userName, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _signUpState.value = signUpState.value.copy(
                    userNameError = result.message
                )

            }

            is Resource.Successful -> {
                _signUpState.value = signUpState.value.copy(
                    userNameError = null
                )

            }
        }
        return signUpState.value.userNameError.isNullOrEmpty()
    }

    private fun validatePhone(phone: String, context: Context): Boolean {
        when (val result = signUpUseCases.checkPhone(phone, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _signUpState.value = signUpState.value.copy(
                    phoneError = result.message
                )

            }

            is Resource.Successful -> {
                _signUpState.value = signUpState.value.copy(
                    phoneError = null
                )

            }
        }
        return signUpState.value.phoneError.isNullOrEmpty()

    }


    fun validateForm(
        context: Context,
        userName: String,
        email: String,
        phone: String,
        password: String,
    ): Boolean {
        return validateUserName(userName, context)
                && validateEmail(email, context)
                && validatePhone(phone, context)
                && validatePassword(password, context)
    }

    suspend fun addUser(name: String, email: String, phone: String, password: String , userType : Int) {

        val content = "$name|$email|$phone"
        val qrBitmap = generateQRCode(content)
        val imageBase64 = bitmapToBase64(qrBitmap)

        signUpUseCases.addUser(name, email, phone, password , userType,imageBase64).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _signUpState.value = signUpState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _signUpState.value = signUpState.value.copy(
                        isLoading = false,
                        signUpErrorMessage = result.message,
                        signUpSuccessful = false
                    )
                }

                is Resource.Successful -> {
                    _signUpState.value = signUpState.value.copy(
                        isLoading = false,
                        signUpErrorMessage = null,
                        signUpSuccessful = true
                    )
                }
            }

        }.launchIn(screenModelScope)
    }

    suspend fun verifyCode(email: String, verifyCode: String) {
        signUpUseCases.verifyCode(email, verifyCode).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _signUpState.value = signUpState.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Error -> {
                    _signUpState.value = signUpState.value.copy(
                        isLoading = false,
                        signUpErrorMessage = result.message
                    )
                }

                is Resource.Successful -> {
                    _signUpState.value = signUpState.value.copy(
                        isLoading = false,
                        signUpSuccessful = true,
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

    fun resetState() {
        _signUpState.value = signUpState.value.copy(
            signUpSuccessful = false,
            signUpErrorMessage = null
        )
    }







}