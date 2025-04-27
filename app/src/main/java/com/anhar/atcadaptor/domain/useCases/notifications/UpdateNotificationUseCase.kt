package com.anhar.atcadaptor.domain.useCases.notifications

import com.anhar.atcadaptor.domain.repository.notifications.NotificationsRepository

class UpdateNotificationUseCase (
    private val notificationsRepository: NotificationsRepository
) {
     operator fun invoke(userId : Int) =
        notificationsRepository.updateNotification(userId = userId)


}