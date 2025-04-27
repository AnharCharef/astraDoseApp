package com.anhar.atcadaptor.domain.useCases.signUp

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.repository.signUp.SignUpRepository
import kotlinx.coroutines.flow.Flow

class VerifyCodeUseCase(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(email : String , verifyCode: String) : Flow<Resource<StandardResponse>> {
        return signUpRepository.verifyCode(email, verifyCode)
    }
}