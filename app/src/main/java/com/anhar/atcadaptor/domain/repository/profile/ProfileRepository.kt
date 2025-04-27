package com.anhar.atcadaptor.domain.repository.profile

import android.content.Context
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun saveAppEntry(key: String, value: String)

    fun getUserInfo(key : String , defaultValue :String) : String

    suspend fun updatePersonalDetails(
        userId: Int,
        name: String,
        email: String,
        phone: String,
        oldPassword: String,
        newPassword: String
    ): Flow<Resource<StandardResponse>>

     fun updateUserQrCode(
         userId : String,
        imageBase64 : String

    ): Flow<Resource<StandardResponse>>

    fun checkUserName(userName : String , context: Context) : Resource<Boolean>
    fun checkEmail (email : String , context: Context) : Resource<Boolean>
    fun checkPhone(phone : String , context : Context) : Resource<Boolean>
    fun checkPassword(password : String,context: Context) : Resource<Boolean>

    suspend fun checkEmailAvailability(userId : Int , email : String ) : Flow<Resource<StandardResponse>>


}