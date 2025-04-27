package com.anhar.atcadaptor.data.remote.calculation

import android.util.Log
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.common.StatusResponse
import com.anhar.atcadaptor.common.Urls.ADD_CALCULATION_HISTORY_URL
import com.anhar.atcadaptor.common.Urls.GET_CALCULATIONS_HISTORY_URL
import com.anhar.atcadaptor.common.Urls.GET_CALCULATION_BY_ID_URL
import com.anhar.atcadaptor.common.Urls.GET_MEDICAMENT_URL
import com.anhar.atcadaptor.common.Urls.GET_USERS_URL
import com.anhar.atcadaptor.data.remote.calculation.dto.GetCalculationByIdResponse
import com.anhar.atcadaptor.data.remote.calculation.dto.GetCalculationsHistoryResponse
import com.anhar.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.anhar.atcadaptor.data.remote.calculation.dto.GetUsersResponse
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

class CalculationService(private val client : HttpClient) {

    fun getMedicaments(): Flow<Resource<GetMedicamentResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val searchQuery: String ,
                val userType : Int ,
            )

            val response = client.get(GET_MEDICAMENT_URL)

            val jsonString = response.bodyAsText()
            Log.d("API_RESPONSE", jsonString)
            val responseBody = response.body<GetMedicamentResponse>()
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

    fun getUsers( searchQuery : String ): Flow<Resource<GetUsersResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val searchQuery: String ,
            )

            val response = client.post(GET_USERS_URL){
                setBody(Data(searchQuery))
            }

            val jsonString = response.bodyAsText()
            Log.d("API_RESPONSE", jsonString)
            val responseBody = response.body<GetUsersResponse>()
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


    fun getCalculationHistory(userId : String): Flow<Resource<GetCalculationsHistoryResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val userId: String
            )

            val response = client.post(GET_CALCULATIONS_HISTORY_URL){
                setBody(Data(userId = userId))
            }

            val jsonString = response.bodyAsText()
            Log.d("API_RESPONSE", jsonString)
            val responseBody = response.body<GetCalculationsHistoryResponse>()
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
    fun getCalculationById(userId : String , calculationId : String ): Flow<Resource<GetCalculationByIdResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val userId: String ,
                val calculationId: String
            )

            val response = client.post(GET_CALCULATION_BY_ID_URL){
                setBody(Data(userId , calculationId))
            }

            val jsonString = response.bodyAsText()
            Log.d("API_RESPONSE", jsonString)
            val responseBody = response.body<GetCalculationByIdResponse>()
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

    fun saveCalculationHistory(calculationData: CalculationData) : Flow<Resource<StandardResponse>> =
        flow{
            try {
                emit(Resource.Loading())
                @Serializable
                data class Data(
                    val calculationData: CalculationData
                )

                val response = client.post(ADD_CALCULATION_HISTORY_URL){
                    setBody(Data(calculationData))
                }

                val jsonString = response.bodyAsText()
                Log.d("API_RESPONSE", jsonString)
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
}