package com.anhar.atcadaptor.data.remote.auth

import android.util.Log
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.common.StatusResponse
import com.anhar.atcadaptor.common.Urls.CHECK_EMAIL_URL
import com.anhar.atcadaptor.common.Urls.LOGIN_URL
import com.anhar.atcadaptor.common.Urls.RESET_PASSWORD_URL
import com.anhar.atcadaptor.common.Urls.SIGNUP_URL
import com.anhar.atcadaptor.common.Urls.VERIFY_CODE_FORGOT_PASSWORD_URL
import com.anhar.atcadaptor.common.Urls.VERIFY_CODE_URL
import com.anhar.atcadaptor.data.remote.auth.dto.LoginResponse
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

class AuthService(private val client: HttpClient) {

    suspend fun addUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        userType : Int,
        imageBase64:String
    ): Flow<Resource<StandardResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val usersName: String,
                val usersEmail: String,
                val usersPassword: String,
                val usersPhone: String,
                val usersType : Int,
                val imageBase64:String
            )

            val response = client.post(SIGNUP_URL) {
                setBody(Data(name, email, password, phone , userType,imageBase64))
            }
            val responseBody = response.body<StandardResponse>()
            if (responseBody.status == StatusResponse.Failure.name.lowercase()) {
                emit(Resource.Error(responseBody.message))
            } else {
                emit(Resource.Successful(responseBody))
            }

            Log.d("SignUpService", response.body())
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

    suspend fun verifyCode(
        email: String,
        verifyCode: String,
        verifyCodeForgotPassword: Boolean = false,
    ): Flow<Resource<StandardResponse>> = flow {
        try {
            @Serializable
            data class Data(
                val email: String,
                val verifycode: String,
            )
            emit(Resource.Loading())
            val response = client.post(
                if (verifyCodeForgotPassword) {
                    VERIFY_CODE_FORGOT_PASSWORD_URL
                } else {
                    VERIFY_CODE_URL
                }
            ) {
                setBody(
                    Data(
                        email = email,
                        verifycode = verifyCode
                    )
                )
            }
            val responseBody = response.body<StandardResponse>()
            if (responseBody.status == StatusResponse.Failure.name.lowercase()) {
                emit(Resource.Error(responseBody.message))
            } else {
                emit(Resource.Successful(responseBody))
            }

            Log.d("SignUpService", response.body())
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

    fun login(email: String, password: String): Flow<Resource<LoginResponse>> = flow {
        try {
            @Serializable
            data class Data(
                val email: String,
                val password: String,
            )
            emit(Resource.Loading())
            val response = client.post(LOGIN_URL) {
                setBody(
                    Data(
                        email = email,
                        password = password
                    )
                )
            }
            val responseBody = response.body<LoginResponse>()
            when (responseBody.status) {
                StatusResponse.Failure.name.lowercase() -> {
                    emit(Resource.Error(responseBody.message))
                }

                StatusResponse.NeedApprove.name -> {
                    emit(Resource.Successful(responseBody, ""))
                }

                else -> {
                    emit(Resource.Successful(responseBody))
                }
            }
            Log.d("SignUpService", response.body())
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

    suspend fun checkEmail(email: String): Flow<Resource<StandardResponse>> = flow {
        try {
            @Serializable
            data class Data(
                val email: String,
            )
            emit(Resource.Loading())
            val response = client.post(CHECK_EMAIL_URL) {
                setBody(
                    Data(
                        email = email
                    )
                )
            }
            val responseBody = response.body<StandardResponse>()
            when (responseBody.status) {
                StatusResponse.Failure.name.lowercase() -> {
                    emit(Resource.Error(responseBody.message))
                }

                else -> {
                    emit(Resource.Successful(responseBody))
                }
            }
            Log.d("SignUpService", response.body())
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

    suspend fun resetPassword(email: String, password: String): Flow<Resource<StandardResponse>> =
        flow {
            try {
                @Serializable
                data class Data(
                    val email: String,
                    val password: String,
                )
                emit(Resource.Loading())
                val response = client.post(RESET_PASSWORD_URL) {
                    setBody(
                        Data(
                            email = email,
                            password = password
                        )
                    )
                }
                val responseBody = response.body<StandardResponse>()
                when (responseBody.status) {
                    StatusResponse.Failure.name.lowercase() -> {
                        emit(Resource.Error(responseBody.message))
                    }

                    else -> {
                        emit(Resource.Successful(responseBody))
                    }
                }
                Log.d("SignUpService", response.body())
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


