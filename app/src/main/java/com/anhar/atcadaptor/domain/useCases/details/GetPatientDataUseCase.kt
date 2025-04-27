package com.anhar.atcadaptor.domain.useCases.details

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.details.dto.PatientDataResponse
import com.anhar.atcadaptor.domain.repository.details.DetailsRepository
import kotlinx.coroutines.flow.Flow

class GetPatientDataUseCase(
    private val detailsRepository: DetailsRepository
) {
    operator fun invoke(patientId : Int? , name : String , email : String , phone : String ) : Flow<Resource<PatientDataResponse>> =
        detailsRepository.getPatientData(patientId = patientId ,name = name ,email = email ,phone = phone )
}