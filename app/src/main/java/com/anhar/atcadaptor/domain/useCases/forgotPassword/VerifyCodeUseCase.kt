package com.anhar.atcadaptor.domain.useCases.forgotPassword

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow

class VerifyCodeUseCase(
    private val forgotPasswordRepository: ForgotPasswordRepository
) {
    suspend operator fun invoke(email : String , verifyCode: String) : Flow<Resource<StandardResponse>> {
        return forgotPasswordRepository.verifyCode(email, verifyCode)
    }
}