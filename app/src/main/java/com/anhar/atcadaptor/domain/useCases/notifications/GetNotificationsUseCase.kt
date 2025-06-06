package com.anhar.atcadaptor.domain.useCases.notifications

import com.anhar.atcadaptor.domain.repository.notifications.NotificationsRepository

class GetNotificationsUseCase(
    private val notificationsRepository: NotificationsRepository
) {
     operator fun invoke(userId : Int , userType : String ) =
         notificationsRepository.getNotifications(userId = userId , userType = userType)


}