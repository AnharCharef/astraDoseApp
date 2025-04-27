package com.anhar.atcadaptor.domain.useCases.forgotPassword

import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow

class ResetPasswordUseCase(
    private val forgotPasswordRepository: ForgotPasswordRepository
) {

    suspend operator fun invoke(email: String , password : String) : Flow<Resource<StandardResponse>> {
        return forgotPasswordRepository.resetPassword(email , password)
    }

}