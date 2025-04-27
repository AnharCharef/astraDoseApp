package com.anhar.atcadaptor.presentation.mainApp.personalDetails

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.model.user.User
import com.anhar.atcadaptor.domain.useCases.profile.ProfileUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonalDetailsViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
) : ScreenModel {

    private var _state = mutableStateOf(PersonalDetailsState())
    val state: State<PersonalDetailsState> = _state

    fun onEvent(event: PersonalDetailsEvent) {
        when (event) {
            is PersonalDetailsEvent.UpdateUserName -> {
                _state.value = _state.value.copy(
                    user = state.value.user.copy(
                        userName = event.name
                    )
                )
            }

            is PersonalDetailsEvent.UpdateEmail -> {

                _state.value = _state.value.copy(
                    user = state.value.user.copy(
                        userEmail = event.email
                    )
                )

            }

            is PersonalDetailsEvent.UpdatePhone -> {
                _state.value = _state.value.copy(
                    user = state.value.user.copy(
                        userPhone = event.phone
                    )
                )
            }

            is PersonalDetailsEvent.UpdatePassword -> {
                _state.value = _state.value.copy(
                    oldPassword = event.password
                )
            }

            is PersonalDetailsEvent.UpdateNewPassword -> {
                _state.value = _state.value.copy(
                    newPassword = event.newPassword
                )
            }

            is PersonalDetailsEvent.ToggleShowPassword -> {
                _state.value = _state.value.copy(
                    showPassword = !state.value.showPassword
                )
            }

            is PersonalDetailsEvent.ToggleShowNewPassword -> {
                _state.value = _state.value.copy(
                    showNewPassword = !state.value.showNewPassword
                )
            }

            is PersonalDetailsEvent.ToggleEditPassword -> {
                _state.value = _state.value.copy(
                    editPassword = !state.value.editPassword
                )
            }

            is PersonalDetailsEvent.ToggleShowDialog -> {
                _state.value = _state.value.copy(
                    showDialog = !state.value.showDialog
                )
            }

            is PersonalDetailsEvent.ToggleShowEmailDialog -> {
                _state.value = _state.value.copy(
                    emailErrorDialog = !state.value.emailErrorDialog
                )
            }

            is PersonalDetailsEvent.UpdatePersonalDetails -> {
                if (validateForm(
                        context = event.context,
                        userName = state.value.user.userName,
                        email = state.value.user.userEmail,
                        oldPassword = state.value.oldPassword,
                        newPassword = state.value.newPassword,
                        phone = state.value.user.userPhone
                    )
                ) {

                    if (state.value.user == event.initialData && !state.value.editPassword) {
                        _state.value = state.value.copy(
                            updateSuccess = true
                        )
                    } else {
                        if (state.value.user.userEmail != event.initialData.userEmail && !state.value.emailVerified) {
                            _state.value = state.value.copy(
                                emailErrorDialog = true,
                                updateError = event.context.getString(R.string.ifYouChangeEmailYouMustVerifyIt)
                            )
                        } else {

                            Log.d(
                                "PersonalDetailsScreen",
                                "editPassword: ${state.value.editPassword}"
                            )

                            if (state.value.editPassword) {
                                event.apply {
                                    if (oldPassword == newPassword) {
                                        _state.value = state.value.copy(
                                            updateError = context.getString(R.string.bothPasswordsAreTheSame),
                                            showDialog = true
                                        )
                                    } else {
//                                        onEvent(PersonalDetailsEvent.SaveUserInformation(state.value.user))
                                        screenModelScope.launch {
                                            updatePersonalDetails(
                                                context,
                                                userId,
                                                name,
                                                email,
                                                phone,
                                                oldPassword,
                                                newPassword,
                                            )
                                        }
                                    }
                                }
                            } else {
                                event.apply {
//                                    onEvent(PersonalDetailsEvent.SaveUserInformation(state.value.user))
                                    screenModelScope.launch {
                                        Log.d("PersonalDetailsScreen", "${state.value}")
                                        updatePersonalDetails(
                                            context,
                                            userId,
                                            name,
                                            email,
                                            phone,
                                            "",
                                            ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is PersonalDetailsEvent.SaveUserInformation -> {
                saveUserData(event.user)
            }

            is PersonalDetailsEvent.CheckEmailAvailability -> {
                screenModelScope.launch {
                    checkEmailAvailability(event.context, event.userId, event.email)
                }
            }

            PersonalDetailsEvent.ToggleUpdatedSuccessState -> {
                _state.value = state.value.copy(
                    updateSuccess = false
                )
            }
        }
    }

    private suspend fun checkEmailAvailability(context: Context, userId: Int, email: String) {
        profileUseCases.checkEmailAvailability(userId, email).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        updateError = context.getString(R.string.thisEmailIsNotAvailable),
                        emailAvailable = false,
                        showDialog = true
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false, updateError = null, emailAvailable = true
                    )
                }
            }
            Log.d("updatePersonalDetails", "updatePersonalDetails: ${result.data}")

        }.launchIn(screenModelScope)
    }

    fun setUserState(user: User, email: String? = null) {
        _state.value = state.value.copy(
            user = user,
            emailVerified = email != null
        )
    }

    private suspend fun updatePersonalDetails(
        context: Context,
        userId: Int,
        name: String,
        email: String,
        phone: String,
        oldPassword: String,
        newPassword: String
    ) {

        profileUseCases.updatePersonalDetails(
           userId =  userId,
            name = name,
            email = email,
            phone = phone,
            oldPassword = oldPassword,
            newPassword = newPassword
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {

                    if (result.data != null) {
                        Log.d("updatePersonalDetails", "error : ${result.data}")
                        _state.value = state.value.copy(
                            isLoading = false,
                            updateError = context.getString(R.string.wrongPassword),
                            updateSuccess = false,
                            showDialog = true
                        )
                    } else {
                        _state.value = state.value.copy(
                            isLoading = false,
                            updateError = result.message,
                            updateSuccess = false,
                            showDialog = true
                        )

                    }

                }

                is Resource.Successful -> {

                    _state.value = state.value.copy(
                        isLoading = false, updateError = null, updateSuccess = true
                    )
                }
            }
            Log.d("updatePersonalDetails", "updatePersonalDetails: ${result.data}")
        }.launchIn(screenModelScope)

    }

    private fun saveUserData(user: User) {
        profileUseCases.saveUserData.let {
            it("userName", user.userName)
            it("userEmail", user.userEmail)
            it("userPhone", user.userPhone)

        }

    }


    private fun validateEmail(email: String, context: Context): Boolean {
        when (val result = profileUseCases.checkEmail(email, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _state.value = state.value.copy(
                    emailError = result.message,
                )
            }

            is Resource.Successful -> {
                _state.value = state.value.copy(
                    emailError = null
                )

            }
        }
        return state.value.emailError.isNullOrEmpty()
    }

    private fun validateOldPassword(password: String, context: Context): Boolean {
        when (val result = profileUseCases.checkPassword(password, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _state.value = state.value.copy(
                    oldPasswordError = result.message,
                )
            }

            is Resource.Successful -> {
                _state.value = state.value.copy(
                    oldPasswordError = null
                )

            }
        }
        return state.value.oldPasswordError.isNullOrEmpty()
    }

    private fun validateNewPassword(password: String, context: Context): Boolean {
        when (val result = profileUseCases.checkPassword(password, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _state.value = state.value.copy(
                    newPasswordError = result.message,
                )
            }

            is Resource.Successful -> {
                _state.value = state.value.copy(
                    newPasswordError = null
                )

            }
        }
        return state.value.newPasswordError.isNullOrEmpty()
    }


    private fun validateUserName(userName: String, context: Context): Boolean {
        when (val result = profileUseCases.checkUserName(userName, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _state.value = state.value.copy(
                    userNameError = result.message
                )

            }

            is Resource.Successful -> {
                _state.value = state.value.copy(
                    userNameError = null
                )

            }
        }
        return state.value.userNameError.isNullOrEmpty()
    }

    private fun validatePhone(phone: String, context: Context): Boolean {
        when (val result = profileUseCases.checkPhone(phone, context)) {
            is Resource.Loading -> {}
            is Resource.Error -> {
                _state.value = state.value.copy(
                    phoneError = result.message
                )

            }

            is Resource.Successful -> {
                _state.value = state.value.copy(
                    phoneError = null
                )

            }
        }
        return state.value.phoneError.isNullOrEmpty()

    }


    private fun validateForm(
        context: Context,
        userName: String,
        email: String,
        phone: String,
        oldPassword: String,
        newPassword: String,
    ): Boolean {
        return if (state.value.editPassword) {

            validateUserName(userName, context) && validateEmail(email, context) && validatePhone(
                phone,
                context
            ) && validateOldPassword(oldPassword, context) && validateNewPassword(
                newPassword,
                context
            )

        } else {

            validateUserName(userName, context) && validateEmail(email, context) && validatePhone(
                phone,
                context
            )

        }

    }

}