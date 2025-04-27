package com.anhar.atcadaptor.domain.useCases.home

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.home.dto.HomeSearchResponse
import com.anhar.atcadaptor.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeSearchUseCase(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(
        searchQuery: String,
        userType: Int,
    ): Flow<Resource<HomeSearchResponse>> {
        return homeRepository.search(searchQuery, userType)
    }
}