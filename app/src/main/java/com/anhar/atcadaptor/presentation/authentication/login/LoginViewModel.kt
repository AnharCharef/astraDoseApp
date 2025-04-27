package com.anhar.atcadaptor.presentation.authentication.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.model.user.User
import com.anhar.atcadaptor.domain.model.user.toUser
import com.anhar.atcadaptor.domain.useCases.login.LoginUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ScreenModel {
    private var _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private var userData = mutableStateOf<User?>(null)

    fun updateEmail(value: String) {
        _loginState.value = loginState.value.copy(
            email = value,
        )
    }

    fun updatePassword(value: String) {
        _loginState.value = loginState.value.copy(
            password = value,
        )
    }

    fun updateRememberMe(value: Boolean) {
        _loginState.value = loginState.value.copy(
            rememberMe = value,
        )
    }

    fun showPassword(showPassword: Boolean) {
        _loginState.value = loginState.value.copy(
            showPassword = showPassword,
        )
    }

    private fun checkEmail(email: String, context: Context): Boolean {

        when (val result = loginUseCases.checkEmail(email, context)) {
            is Resource.Loading -> {
                // Handle loading state
            }

            is Resource.Error -> {
                _loginState.value = loginState.value.copy(
                    emailError = result.message,
                )
            }

            is Resource.Successful -> {
                _loginState.value = loginState.value.copy(
                    emailError = null
                )

            }
        }
        return loginState.value.emailError.isNullOrEmpty()
    }

    private fun checkPassword(password: String, context: Context): Boolean {


        when (val result = loginUseCases.checkPassword(password, context)) {
            is Resource.Loading -> {
                // Handle loading state
            }

            is Resource.Error -> {
                _loginState.value = loginState.value.copy(
                    passwordError = result.message,
                )
            }

            is Resource.Successful -> {
                _loginState.value = loginState.value.copy(
                    passwordError = null
                )
            }
        }

        return loginState.value.passwordError.isNullOrEmpty()
    }

    fun validateForm(email: String, password: String, context: Context): Boolean {

        return checkEmail(email, context) && checkPassword(password, context)


    }

    suspend fun login(email: String, password: String) {
        loginUseCases.login(email, password).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginState.value = loginState.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Error -> {
                    _loginState.value = loginState.value.copy(
                        isLoading = false,
                        loginError = result.message,
                        loginSuccessful = false,
                    )
                    Log.d("LoginViewModel", "result : ${result.message}")
                }

                is Resource.Successful -> {
                    if (result.message != null) {
                        _loginState.value = loginState.value.copy(
                            isLoading = false,
                            loginSuccessful = false,
                            loginError = null,
                            needUserApprove = true
                        )
                    } else {
                        if (result.data?.data != null) {
                            userData.value = result.data.data.toUser()
                        }
                        _loginState.value = loginState.value.copy(
                            isLoading = false,
                            loginSuccessful = true,
                            loginError = null,
                            needUserApprove = false
                        )

                    }

                }
            }

        }.launchIn(screenModelScope)
    }

    fun resetState() {
        _loginState.value = loginState.value.copy(
            loginSuccessful = false,
            loginError = null,
            needUserApprove = null
        )
    }

    fun saveAppEntry(key: String, value: String) {
        loginUseCases.saveAppEntry?.let { it(key, value) }
    }

    fun saveUserInformation() {
        userData.value?.let { user ->
            loginUseCases.saveAppEntry?.let {
                it("userName", user.userName)
                it("userId", user.userId.toString())

                it("userEmail", user.userEmail)
                it("userPhone", user.userPhone)
                
                it("userType" , user.userType.toString())
                it("subscribeToTopics" , true.toString())
                it("userQrImage" , user.userQrImage)
            }
            FirebaseMessaging.getInstance().subscribeToTopic("patients")
            FirebaseMessaging.getInstance().subscribeToTopic(user.userId.toString())
        }

    }

}