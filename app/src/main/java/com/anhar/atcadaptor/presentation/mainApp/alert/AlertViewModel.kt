package com.anhar.atcadaptor.presentation.mainApp.alert

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.model.notification.toNotifications
import com.anhar.atcadaptor.domain.useCases.notifications.NotificationsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AlertViewModel @Inject constructor(
    private val notificationsUseCases: NotificationsUseCases
): ScreenModel{

    private var _state = mutableStateOf(AlertState())
    val state : State<AlertState> = _state


     fun getNotifications(userId: Int) {
        notificationsUseCases.getNotifications(userId).onEach {result->
            when(result){
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = result.data?.message ?: result.message?: "An Unexpected Error Occurred"
                    )
                }
                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null,
                        notifications =  result.data?.data?.toNotifications() ?: emptyList()
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

     fun updateNotifications(userId: Int) {
        notificationsUseCases.updateNotification(userId)
    }


}