package com.anhar.atcadaptor.domain.repository.signUp

import android.content.Context
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    fun checkUserName(userName : String , context: Context) : Resource<Boolean>
    fun checkEmail (email : String , context: Context) : Resource<Boolean>//Flow<Resource<Boolean>>
    fun checkPhone(phone : String , context : Context) : Resource<Boolean>
    fun checkPassword(password : String,context: Context) : Resource<Boolean>// Flow<Resource<Boolean>>
    suspend fun addUser(name : String , email : String , phone : String , password : String , userType: Int,imageBase64:String) : Flow<Resource<StandardResponse>>
    suspend fun verifyCode(email  :String , verifyCode: String) : Flow<Resource<StandardResponse>>
}