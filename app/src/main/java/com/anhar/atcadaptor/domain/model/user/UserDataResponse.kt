package com.anhar.atcadaptor.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    val users_approve: Int,
    val users_created_at: String,
    val users_email: String,
    val users_id: Int,
    val users_name: String,
    val users_password: String,
    val users_phone: String,
    val users_verifycode: Int ,
    val users_type : Int,
    val users_qr_image :String
)

fun UserDataResponse.toUser(): User {
    return User(
        userName = users_name,
        userId = users_id,
        userEmail = users_email,
        userPhone = users_phone ,
        userType = users_type ,
        userQrImage = users_qr_image
    )
}
