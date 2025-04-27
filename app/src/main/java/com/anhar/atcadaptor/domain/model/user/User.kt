package com.anhar.atcadaptor.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId : Int = 0 ,
    val userName : String= "",
    val userEmail : String ="",
    val userPhone : String ="",
    val userImage : String ? = null ,
    val userType : Int = 0 ,
    val userQrImage : String =""
) : java.io.Serializable
