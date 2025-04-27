package com.anhar.atcadaptor.presentation.mainApp.alert

import com.anhar.atcadaptor.domain.model.notification.Notification

data class AlertState(
    val isLoading : Boolean = false ,
    val error : String? = null ,
    val notifications : List<Notification> = emptyList()
)
