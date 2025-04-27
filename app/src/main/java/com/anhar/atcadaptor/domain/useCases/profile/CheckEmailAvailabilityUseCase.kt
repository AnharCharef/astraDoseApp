package com.anhar.atcadaptor.domain.useCases.profile

import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository

class CheckEmailAvailabilityUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userId: Int , email : String) =
        profileRepository.checkEmailAvailability(userId , email)

}