package com.anhar.atcadaptor.data.remote.auth.dto

import com.anhar.atcadaptor.domain.model.user.UserDataResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val data: UserDataResponse? = null,
    val message: String,
    val status: String
)