package com.anhar.atcadaptor.presentation.authentication.forgotPassword

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.forgotPassword.ForgotPasswordUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class ForgotPasswordViewModel@Inject constructor(
    private val forgotPasswordUseCases: ForgotPasswordUseCases
) : ScreenModel {

    private var _forgotPasswordState = mutableStateOf(ForgotPasswordState())
    val forgotPasswordState : State<ForgotPasswordState> = _forgotPasswordState

    fun setState(newState: ForgotPasswordState) {
        _forgotPasswordState.value = newState
    }

    fun updateEmail(value : String){
        _forgotPasswordState.value = forgotPasswordState.value.copy(
            email = value
        )
    }

    fun updatePassword (value : String){
        _forgotPasswordState.value = forgotPasswordState.value.copy(
            newPassword = value
        )
    }

    fun updateVerificationCode(value : String){
        _forgotPasswordState.value = forgotPasswordState.value.copy(
            verificationCode = value
        )
    }

    fun showPassword(showPassword : Boolean){
        _forgotPasswordState.value = forgotPasswordState.value.copy(
            showPassword = showPassword
        )
    }

     fun checkEmail(email: String,context: Context): Boolean {

        when (val result = forgotPasswordUseCases.checkEmail(email,context)) {
            is Resource.Loading -> {
                // Handle loading state
            }

            is Resource.Error -> {
                _forgotPasswordState.value = forgotPasswordState.value.copy(
                    emailError = result.message,
                )
            }

            is Resource.Successful -> {
                _forgotPasswordState.value = _forgotPasswordState.value.copy(
                    emailError = null
                )

            }
        }
        return forgotPasswordState.value.emailError.isNullOrEmpty()
    }

     fun checkPassword(password: String,context: Context): Boolean {


        when (val result = forgotPasswordUseCases.checkPassword(password,context)) {
            is Resource.Loading -> {
                // Handle loading state
            }

            is Resource.Error -> {
                _forgotPasswordState.value = forgotPasswordState.value.copy(
                    passwordError = result.message ,
                )
            }

            is Resource.Successful -> {
                _forgotPasswordState.value = forgotPasswordState.value.copy(
                    passwordError = null
                )
            }
        }

        return forgotPasswordState.value.passwordError.isNullOrEmpty()
    }

    suspend fun checkEmail(email:String) {
        forgotPasswordUseCases.checkEmailDb(email).onEach { result->
            when(result){
                is Resource.Loading->{
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error ->{
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        checkEmailError = result.message,
                        checkEmailSuccessful = false
                    )
                }
                is Resource.Successful-> {
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        checkEmailError = null,
                        checkEmailSuccessful = true
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

    suspend fun verifyCode(email : String, verifyCode : String){
        forgotPasswordUseCases.verifyCode(email,verifyCode).onEach { result ->
            when(result){
                is Resource.Loading->{
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error ->{
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        verificationErrorMessage = result.message,
                        verificationCodeError = true
                    )
                }
                is Resource.Successful-> {
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        verificationErrorMessage = null,
                        verificationCodeError = false
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

    suspend fun resetPassword(email : String, password : String){
        forgotPasswordUseCases.resetPassword(email,password).onEach { result ->
            when(result){
                is Resource.Loading->{
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error ->{
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        passwordError = result.message,
                        resetPasswordSuccessful = false
                    )
                }
                is Resource.Successful-> {
                    _forgotPasswordState.value = forgotPasswordState.value.copy(
                        isLoading = false,
                        passwordError = null,
                        resetPasswordSuccessful = true
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

    fun resetState(){
        _forgotPasswordState.value = forgotPasswordState.value.copy(
            checkEmailError = null,
            checkEmailSuccessful = false,
            verificationCodeError = null,
            verificationErrorMessage = null,
            resetPasswordSuccessful = false,
            resetPasswordError = null
        )
    }
}