package com.anhar.atcadaptor.domain.repository.details

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.details.dto.DetailsResponse
import com.anhar.atcadaptor.data.remote.details.dto.PatientDataResponse
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getDetails (medicationName : String ,patientId : Int ?= null ) : Flow<Resource<DetailsResponse>>
    fun getPatientData (patientId : Int ?= null, name : String , email : String , phone : String ) : Flow<Resource<PatientDataResponse>>

}