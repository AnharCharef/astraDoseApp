package com.anhar.atcadaptor.domain.useCases.signUp

import android.content.Context
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository
import com.anhar.atcadaptor.domain.repository.signUp.SignUpRepository

class CheckPhoneUseCase(
    private val signUpRepository: SignUpRepository? = null,
    private val profileRepository: ProfileRepository? = null
) {
    operator fun invoke(phone: String, context: Context): Resource<Boolean> {
        return signUpRepository?.checkPhone(phone, context)
            ?:profileRepository?.checkPhone(phone, context)
            ?:Resource.Error("")
    }

}