package com.anhar.atcadaptor.domain.useCases.calculation

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.calculation.dto.GetCalculationsHistoryResponse
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetCalculationsHistoryUseCase(
    private val calculationRepository : CalculationRepository
){
    operator fun invoke(userId :String ) : Flow<Resource<GetCalculationsHistoryResponse>> {
        return calculationRepository.getCalculationsHistory(userId)
    }
}