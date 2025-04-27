package com.anhar.atcadaptor.domain.useCases.login

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.auth.dto.LoginResponse
import com.anhar.atcadaptor.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email : String , password : String) : Flow<Resource<LoginResponse>>{
        return loginRepository.login(email , password)
    }
}