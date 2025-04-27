package com.anhar.atcadaptor.domain.useCases.profile

import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository

class GetUserDataUseCase(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(key  :String , defaultValue : String) : String =
        profileRepository.getUserInfo(key , defaultValue)
}