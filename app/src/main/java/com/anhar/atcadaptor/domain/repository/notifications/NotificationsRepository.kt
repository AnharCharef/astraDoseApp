package com.anhar.atcadaptor.domain.repository.notifications


import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.alert.dto.NotificationsResponse
import com.anhar.atcadaptor.data.remote.dto.StandardResponse

import kotlinx.coroutines.flow.Flow

interface NotificationsRepository  {

     fun getNotifications(userId : Int) : Flow<Resource<NotificationsResponse>>
     fun sendNotification(topic : String ,title : String , body : String , sender: String) : Flow<Resource<StandardResponse>>
     fun updateNotification(userId : Int)

}