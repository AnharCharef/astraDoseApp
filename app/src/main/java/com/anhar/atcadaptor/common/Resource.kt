package com.anhar.atcadaptor.common

sealed class Resource<T>(val data: T?= null , val message : String? = null) {

    class Successful<T>(data: T? , message: String? =null) :Resource<T>(data,message)

    class Error<T>(message: String? , data : T? = null) : Resource<T>(data , message)

    class Loading<T>(data: T? = null) : Resource<T>(data)

}

enum class StatusResponse {
    Success, Failure , NeedApprove
}

// ENUM for user roles
enum class UserRole { Doctor, Patient }

enum class Genre {
    Homme, Femme
}

enum class Race{
    Non_Afro_Americain , Afro_Americain
}

enum class DFGType{
    MDRD , CG
}