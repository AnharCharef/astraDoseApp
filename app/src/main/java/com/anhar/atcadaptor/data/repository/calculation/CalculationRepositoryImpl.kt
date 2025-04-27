package com.anhar.atcadaptor.data.repository.calculation

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.calculation.CalculationService
import com.anhar.atcadaptor.data.remote.calculation.dto.GetCalculationByIdResponse
import com.anhar.atcadaptor.data.remote.calculation.dto.GetCalculationsHistoryResponse
import com.anhar.atcadaptor.data.remote.calculation.dto.GetMedicamentResponse
import com.anhar.atcadaptor.data.remote.calculation.dto.GetUsersResponse
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.flow.Flow

class CalculationRepositoryImpl(private val calculationService: CalculationService) :
    CalculationRepository {
    override fun getMedicament(): Flow<Resource<GetMedicamentResponse>> {
        return calculationService.getMedicaments()
    }

    override fun getUsers(searchQuery: String): Flow<Resource<GetUsersResponse>> {
        return calculationService.getUsers(searchQuery)
    }

    override fun getCalculationsHistory(userId: String): Flow<Resource<GetCalculationsHistoryResponse>> {
        return calculationService.getCalculationHistory(userId)
    }

    override fun getCalculationById(
        userId: String,
        calculationId: String
    ): Flow<Resource<GetCalculationByIdResponse>> {
        return calculationService.getCalculationById(userId = userId, calculationId = calculationId)
    }

    override fun addCalculationHistory(calculationData: CalculationData): Flow<Resource<StandardResponse>> {
        return calculationService.saveCalculationHistory(calculationData)
    }
}