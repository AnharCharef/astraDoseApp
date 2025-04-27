package com.anhar.atcadaptor.domain.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationsData(
    val notifications_body: String,
    val notifications_id: Int,
    val notifications_status: Int,
    val notifications_time: String,
    val notifications_title: String,
    val notifications_topic: String
)

fun List<NotificationsData>.toNotifications () : List<Notification> {
    return this.map {  Notification(
        title = it.notifications_title ,
        body = it.notifications_body ,
        id = it.notifications_id ,
        unread = it.notifications_status == 0 ,
        time = it.notifications_time
    )
    }
}