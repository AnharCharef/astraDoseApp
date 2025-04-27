package com.anhar.atcadaptor.domain.useCases.calculation

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.calculation.dto.GetUsersResponse
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val calculationRepository: CalculationRepository
) {
    operator fun invoke(searchQuery: String): Flow<Resource<GetUsersResponse>> {
        return calculationRepository.getUsers(searchQuery)
    }
}