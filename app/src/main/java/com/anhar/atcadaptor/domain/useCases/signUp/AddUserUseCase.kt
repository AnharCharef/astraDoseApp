package com.anhar.atcadaptor.domain.useCases.signUp

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.repository.signUp.SignUpRepository

import kotlinx.coroutines.flow.Flow

class AddUserUseCase(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(name : String , email : String , phone : String , password : String , userType : Int ,imageBase64:String) : Flow<Resource<StandardResponse>> {
        return signUpRepository.addUser(name , email , phone, password,userType,imageBase64)
    }

}