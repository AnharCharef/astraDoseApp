package com.anhar.atcadaptor.domain.useCases.calculation

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.calculation.dto.GetCalculationByIdResponse
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetCalculationByIdUseCase(
    private val calculationRepository : CalculationRepository
){
    operator fun invoke(userId : String , calculationId : String) : Flow<Resource<GetCalculationByIdResponse>> {
        return calculationRepository.getCalculationById(userId , calculationId)
    }
}