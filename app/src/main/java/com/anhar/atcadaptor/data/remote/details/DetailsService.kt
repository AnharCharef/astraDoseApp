package com.anhar.atcadaptor.data.remote.details

import android.util.Log
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.common.StatusResponse
import com.anhar.atcadaptor.common.Urls.GET_DETAILS_URL
import com.anhar.atcadaptor.common.Urls.GET_PATIENT_DATA_URL
import com.anhar.atcadaptor.data.remote.details.dto.DetailsResponse
import com.anhar.atcadaptor.data.remote.details.dto.PatientDataResponse
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

class DetailsService(private val client: HttpClient) {

    fun getDetails(
        medicationName: String,
        patientId: Int? = null
    ): Flow<Resource<DetailsResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val medicationName: String,
                val patientId: Int?,
            )

            Log.d("medicationName", "$medicationName , $patientId")

            val response = client.post(GET_DETAILS_URL) {
                setBody(Data(medicationName, patientId))
            }
            val responseBody = response.body<DetailsResponse>()
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

    fun getPatientData(
        patientId: Int? = null,
        name: String,
        email: String,
        phone: String
    ): Flow<Resource<PatientDataResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val patientId: Int?,
                val userEmail: String,
                val userPhone: String,
                val userName: String
            )

            Log.d("GetPatientData" ,"id: $patientId , email : $email , name : $name , phone : $phone")
            val response = client.post(GET_PATIENT_DATA_URL) {
                setBody(Data(patientId = patientId,userName = name , userEmail=email , userPhone = phone))
            }
            val responseBody = response.body<PatientDataResponse>()
            if (responseBody.status == StatusResponse.Failure.name.lowercase()) {
                emit(Resource.Error(responseBody.message))
            } else {
                emit(Resource.Successful(responseBody))
            }

            Log.d("GetPatientData", response.body())
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