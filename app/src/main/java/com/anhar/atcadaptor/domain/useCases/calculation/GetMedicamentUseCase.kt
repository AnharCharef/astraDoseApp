package com.anhar.atcadaptor.domain.useCases.calculation

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

data class GetMedicamentUseCase(
    private val calculationRepository : CalculationRepository
){
    operator fun invoke() : Flow<Resource<GetMedicamentResponse>>{
        return calculationRepository.getMedicament()
    }
}
