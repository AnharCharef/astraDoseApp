package com.anhar.atcadaptor.presentation.mainApp.asking

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.notifications.NotificationsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AskingViewModel @Inject constructor(
    private val notificationsUseCases: NotificationsUseCases
) : ScreenModel {


    private var _state = mutableStateOf(AskingState())
    val state: State<AskingState> = _state

    fun onEvent(event: AskingEvent) {
        when (event) {
            is AskingEvent.OnAskValueChange -> {
                _state.value = state.value.copy(
                    askingValue = event.value
                )
            }

            is AskingEvent.OnObjectValueChange -> {
                _state.value = state.value.copy(
                    objectValue = event.value
                )
            }

            is AskingEvent.SendNotification -> {

                event.apply {
                    if(title.isNotEmpty() && body.isNotEmpty()) {
                        sendNotification(topic = topic, title = title, body = body, sender = sender)
                    }else{
                        _state.value = state.value.copy(
                            error = "Please fill all values"
                        )
                    }
                }
            }

            AskingEvent.ResetState -> {
                _state.value = AskingState()
            }
        }
    }

    private fun sendNotification(
        topic: String,
        title: String,
        body: String,
        sender: String
    ) {
        notificationsUseCases.sendNotification(topic = topic, title = title, body = body, sender = sender).onEach {result ->
            when (result){

                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = result.message?:result.data?.message?: "An Unexpected Error"
                    )
                }
                is Resource.Successful ->{
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null ,
                        success = true
                    )
                }
            }

            Log.d("TagA" , "result : ${result.data} message = ${result.message}")
        }.launchIn(screenModelScope)
    }

}