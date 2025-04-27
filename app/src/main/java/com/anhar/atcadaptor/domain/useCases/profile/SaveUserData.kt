package com.anhar.atcadaptor.domain.useCases.profile

import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository

class SaveUserData (
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(key : String , value: String){
        profileRepository.saveAppEntry(key , value)
    }

}