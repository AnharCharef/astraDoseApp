package com.anhar.atcadaptor.data.remote.profile

import android.util.Log
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.common.Urls.CHECK_EMAIL_AVAILABILITY_URL
import com.anhar.atcadaptor.common.Urls.UPDATE_PERSONAL_DETAILS_URL
import com.anhar.atcadaptor.common.Urls.UPDATE_USER_QR_CODE_URL
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

class ProfileService(private val client: HttpClient) {



    suspend fun updatePersonalDetails(
        userId: Int,
        name: String, email: String, phone: String,
        oldPassword: String, newPassword: String,
    ): Flow<Resource<StandardResponse>> = flow {
        try {
            emit(Resource.Loading())

            @Serializable
            data class Body(
                val userId: Int,
                val name: String,
                val email: String,
                val phone: String,
                val oldPassword: String,
                val newPassword: String
            )

            val response = client.post(UPDATE_PERSONAL_DETAILS_URL) {
                setBody(Body(
                    userId = userId,
                    name =name,
                    email = email,
                    phone = phone,
                    oldPassword =oldPassword,
                    newPassword=newPassword ,
                   ))
            }
            val responseBody = response.body<StandardResponse>()
            if (responseBody.status == "success") {
                emit(Resource.Successful(responseBody))
            } else {
                if (responseBody.status == "password")
                    emit(Resource.Error(message = responseBody.message, data = responseBody))
                else
                    emit(Resource.Error(responseBody.message))
            }
            Log.d("ProfileService", "response : $responseBody")

        } catch (e: ClientRequestException) {
            emit(Resource.Error("Client request error"))
            Log.d("SignUpService", "Client request error: ${e.localizedMessage}")
        } catch (e: ServerResponseException) {
            emit(Resource.Error("Server response error"))
            Log.d("SignUpService", "Server response error: ${e.localizedMessage}")
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
            Log.d("SignUpService", "Couldn't reach server: ${e.message}")
        } catch (e: SerializationException) {
            emit(Resource.Error("Serialization error"))
            Log.d("SignUpService", "Serialization error: ${e.localizedMessage}")
        }
    }

    suspend fun checkEmailAvailability(userId: Int, email: String): Flow<Resource<StandardResponse>> =
        flow {

            try {
                emit(Resource.Loading())
                @Serializable
                data class Body(
                    val userId: Int,
                    val email: String,
                )

                val response = client.post(CHECK_EMAIL_AVAILABILITY_URL) {
                    setBody(Body(userId, email))
                }
                val responseBody = response.body<StandardResponse>()
                if (responseBody.status == "success") {
                    emit(Resource.Successful(responseBody))
                } else {
                    emit(Resource.Error(responseBody.message))
                }
                Log.d("ProfileService", "response : $responseBody")

            } catch (e: ClientRequestException) {
                emit(Resource.Error("Client request error"))
                Log.d("SignUpService", "Client request error: ${e.localizedMessage}")
            } catch (e: ServerResponseException) {
                emit(Resource.Error("Server response error"))
                Log.d("SignUpService", "Server response error: ${e.localizedMessage}")
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server"))
                Log.d("SignUpService", "Couldn't reach server: ${e.message}")
            } catch (e: SerializationException) {
                emit(Resource.Error("Serialization error"))
                Log.d("SignUpService", "Serialization error: ${e.localizedMessage}")
            }
        }

    fun updateUserQrCode(userId : String , imageBase64: String): Flow<Resource<StandardResponse>> = flow {
        try {
            emit(Resource.Loading())

            @Serializable
            data class Body(
                val userId: String,
                val imageBase64: String ,
            )

            Log.d("ProfileService" , userId +" fds "+ imageBase64)

            val response = client.post(UPDATE_USER_QR_CODE_URL) {
                setBody(Body(
                    userId = userId ,
                    imageBase64 = imageBase64
                ))
            }
            val responseBody = response.body<StandardResponse>()
            if (responseBody.status == "success") {
                emit(Resource.Successful(responseBody))
            } else {
                emit(Resource.Error(responseBody.message))
            }
            Log.d("ProfileService", "response : $responseBody")

        } catch (e: ClientRequestException) {
            emit(Resource.Error("Client request error"))
            Log.d("SignUpService", "Client request error: ${e.localizedMessage}")
        } catch (e: ServerResponseException) {
            emit(Resource.Error("Server response error"))
            Log.d("SignUpService", "Server response error: ${e.localizedMessage}")
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
            Log.d("SignUpService", "Couldn't reach server: ${e.message}")
        } catch (e: SerializationException) {
            emit(Resource.Error("Serialization error"))
            Log.d("SignUpService", "Serialization error: ${e.localizedMessage}")
        }
    }

}