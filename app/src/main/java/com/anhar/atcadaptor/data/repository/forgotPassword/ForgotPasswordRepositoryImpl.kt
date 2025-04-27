package com.anhar.atcadaptor.data.repository.forgotPassword

import android.content.Context
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.auth.AuthService
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import kotlinx.coroutines.flow.Flow

class ForgotPasswordRepositoryImpl(
    private val authService: AuthService
): ForgotPasswordRepository {
    override fun checkEmail(email: String , context: Context): Resource<Boolean> {
        return Constant.checkEmail(email ,context)
    }

    override fun checkPassword(password: String , context: Context): Resource<Boolean> {
        return Constant.checkPassword(password, context)
    }

    override suspend fun checkEmail(email: String): Flow<Resource<StandardResponse>> {
        return authService.checkEmail(email)
    }

    override suspend fun verifyCode(
        email: String,
        verifyCode: String
    ): Flow<Resource<StandardResponse>> {
        return authService.verifyCode(email,verifyCode, verifyCodeForgotPassword = true)
    }

    override suspend fun resetPassword(
        email: String,
        password: String
    ): Flow<Resource<StandardResponse>> {
        return authService.resetPassword(email, password)
    }
}