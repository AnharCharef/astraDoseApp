package com.anhar.atcadaptor.domain.useCases.home

import com.anhar.atcadaptor.domain.useCases.appEntry.SaveAppEntryUseCase

data class HomeUseCases(
    val search : HomeSearchUseCase,
    val saveAppEntry : SaveAppEntryUseCase?=null,
)
