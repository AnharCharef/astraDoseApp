package com.anhar.atcadaptor.domain.useCases.calculation

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class AddCalculationHistoryUseCase ( private val calculationRepository : CalculationRepository
){
    operator fun invoke(calculationData: CalculationData ) : Flow<Resource<StandardResponse>> {
        return calculationRepository.addCalculationHistory(calculationData)
    }
}