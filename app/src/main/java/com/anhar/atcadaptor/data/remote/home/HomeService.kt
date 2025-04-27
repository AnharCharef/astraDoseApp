package com.anhar.atcadaptor.data.remote.home

import android.util.Log
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.common.StatusResponse
import com.anhar.atcadaptor.common.Urls.HOME_SEARCH_URL
import com.anhar.atcadaptor.data.remote.home.dto.HomeSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

class HomeService (private val client: HttpClient) {

    fun search(searchQuery: String , userType : Int): Flow<Resource<HomeSearchResponse>> = flow {
        try {
            emit(Resource.Loading())
            @Serializable
            data class Data(
                val searchQuery: String ,
                val userType : Int ,
            )

            val response = client.post(HOME_SEARCH_URL) {
                setBody(Data(searchQuery , userType))
            }
            val jsonString = response.bodyAsText()
            Log.d("API_RESPONSE", jsonString)
            val responseBody = response.body<HomeSearchResponse>()
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