package com.anhar.atcadaptor.presentation.mainApp.asking

sealed class AskingEvent {

    data class OnObjectValueChange(val value: String) : AskingEvent()

    data class OnAskValueChange(val value: String) : AskingEvent()

    data class SendNotification(
        val topic: String,
        val title: String,
        val body: String,
        val sender: String
    ) : AskingEvent()

    data object ResetState : AskingEvent()


}
