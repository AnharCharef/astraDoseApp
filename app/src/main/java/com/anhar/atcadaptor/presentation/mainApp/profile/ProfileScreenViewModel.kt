package com.anhar.atcadaptor.presentation.mainApp.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.common.Constant.bitmapToBase64
import com.anhar.atcadaptor.common.Constant.generateQRCode
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.appEntry.AppEntryUseCases
import com.anhar.atcadaptor.domain.useCases.profile.ProfileUseCases
import com.anhar.atcadaptor.presentation.components.isNotificationPermissionGranted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileScreenViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val appEntryUseCases: AppEntryUseCases,
) : ScreenModel {

    private var _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SetUserId -> {
                _state.value = state.value.copy(
                    user = state.value.user.copy(
                        userId = event.userId
                    )
                )
            }

            is ProfileEvent.Logout -> {
                logout()
                onEvent(ProfileEvent.HideDialog)
            }

            is ProfileEvent.ShowDialog -> {
                _state.value = state.value.copy(
                    showDialog = true
                )
            }

            is ProfileEvent.HideDialog -> {
                _state.value = state.value.copy(
                    showDialog = false
                )
            }

            is ProfileEvent.ToggleNotification -> {
                if (event.value) {
                    profileUseCases.saveUserData("subscribeToTopics", true.toString())
                    FirebaseMessaging.getInstance().subscribeToTopic(if(state.value.user.userType == 0) "patients" else "doctors")
                    FirebaseMessaging.getInstance().subscribeToTopic(state.value.user.userId.toString())
                } else {
                    profileUseCases.saveUserData("subscribeToTopics", false.toString())
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(if(state.value.user.userType == 0) "patients" else "doctors")
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(state.value.user.userId.toString())
                }
                _state.value = state.value.copy(
                    isNotificationEnable = event.value
                )
            }


            is ProfileEvent.GetUserData -> {
                getUserData()
                val isNotificationEnable =
                    profileUseCases.getUserData("subscribeToTopics", false.toString()).toBoolean()
                            && isNotificationPermissionGranted(event.context)
                _state.value = state.value.copy(
                    isNotificationEnable = isNotificationEnable
                )
            }

            ProfileEvent.UpdateUserQrCode -> {
                updateUserQrCode()
            }
        }
    }

    private fun logout() {
        profileUseCases.saveUserData(APP_ENTRY, "1")
        FirebaseMessaging.getInstance().unsubscribeFromTopic(if(state.value.user.userType == 0 ) "patients" else "doctors")
        FirebaseMessaging.getInstance().unsubscribeFromTopic(state.value.user.userId.toString())
    }

    private fun getUserData() {
        val userId = profileUseCases.getUserData("userId", "")
        val userName = profileUseCases.getUserData("userName", "")
        val userEmail = profileUseCases.getUserData("userEmail", "")
        val userPhone = profileUseCases.getUserData("userPhone", "")
        val userImage = profileUseCases.getUserData("userQrImage", "")

        _state.value = state.value.copy(
            user = state.value.user.copy(
                userId = if (userId.isNotEmpty()) userId.toInt() else state.value.user.userId,
                userName = userName.ifEmpty { state.value.user.userName },
                userEmail = userEmail.ifEmpty { state.value.user.userEmail },
                userPhone = userPhone.ifEmpty { state.value.user.userPhone },
                userImage = userImage.ifEmpty { state.value.user.userName },
            )
        )
    }

    fun saveAppEntry(value: String) {
        appEntryUseCases.saveAppEntryUseCase(APP_ENTRY, value)
    }

    private fun updateUserQrCode() {
        val imageBase64 = reGenerateQrCode(
            state.value.user.userName,
            state.value.user.userEmail,
            state.value.user.userPhone
        )
        profileUseCases.updateUserQrCode(
            userId = state.value.user.userId.toString(),
            imageBase64 = imageBase64
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: result.data?.message ?: "An Unexpected Error"
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = null,
                        user = state.value.user.copy(
                            userImage = imageBase64
                        )
                    )
                    appEntryUseCases.saveAppEntryUseCase("userQrImage" , imageBase64)
                }
            }
        }.launchIn(screenModelScope)
    }


    private fun reGenerateQrCode(name: String, email: String, phone: String): String {
        val content = "$name|$email|$phone"
        val qrBitmap = generateQRCode(content)
        return bitmapToBase64(qrBitmap)
    }


}