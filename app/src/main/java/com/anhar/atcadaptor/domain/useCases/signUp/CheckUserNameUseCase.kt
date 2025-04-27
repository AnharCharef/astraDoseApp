package com.anhar.atcadaptor.domain.useCases.signUp

import android.content.Context
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository
import com.anhar.atcadaptor.domain.repository.signUp.SignUpRepository

class CheckUserNameUseCase (
    private val signUpRepository: SignUpRepository? = null,
    private val profileRepository: ProfileRepository? = null
){
    operator fun invoke(userName : String , context : Context) : Resource<Boolean> {
        return signUpRepository?.checkUserName(userName,context)
            ?:profileRepository?.checkUserName(userName,context)
            ?:Resource.Error("")
    }

}