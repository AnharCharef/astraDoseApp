package com.anhar.atcadaptor.domain.useCases.profile

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserQrCodeUseCase(
    private val profileRepository: ProfileRepository
) {
     operator fun invoke( userId : String,imageBase64 : String , ): Flow<Resource<StandardResponse>> =
        profileRepository.updateUserQrCode(userId= userId, imageBase64 = imageBase64)
}