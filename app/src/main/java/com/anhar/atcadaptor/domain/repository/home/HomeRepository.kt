package com.anhar.atcadaptor.domain.repository.home

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.home.dto.HomeSearchResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
   suspend fun search(searchQuery: String , userType : Int): Flow<Resource<HomeSearchResponse>>
}