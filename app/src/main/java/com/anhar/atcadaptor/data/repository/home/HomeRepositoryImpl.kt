package com.anhar.atcadaptor.data.repository.home

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.home.HomeService
import com.anhar.atcadaptor.data.remote.home.dto.HomeSearchResponse
import com.anhar.atcadaptor.domain.repository.home.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun search(searchQuery: String , userType : Int): Flow<Resource<HomeSearchResponse>> {
        return homeService.search(searchQuery, userType)
    }

}