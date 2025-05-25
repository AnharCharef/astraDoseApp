package com.anhar.atcadaptor.common

object Urls {

    private const val BASE_URL = "http://192.168.1.8:8080/atc_adaptor/"

    /** auth urls */
    const val SIGNUP_URL = "$BASE_URL/auth/signup.php"
    const val VERIFY_CODE_URL = "$BASE_URL/auth/verifyCode.php"
    const val LOGIN_URL = "$BASE_URL/auth/login.php"
    const val CHECK_EMAIL_URL = "$BASE_URL/auth/forgotPassword/checkEmail.php"
    const val VERIFY_CODE_FORGOT_PASSWORD_URL = "$BASE_URL/auth/forgotPassword/verifycode.php"
    const val RESET_PASSWORD_URL = "$BASE_URL/auth/forgotPassword/resetPassword.php"

    /** home urls */
    const val HOME_SEARCH_URL = "$BASE_URL/home/homeSearch.php"

    /** details urls */
    const val GET_DETAILS_URL = "$BASE_URL/details/getDetails.php"
    const val GET_PATIENT_DATA_URL = "$BASE_URL/details/getPatientDetails.php"

    /** profile urls */
    const val UPDATE_PERSONAL_DETAILS_URL = "$BASE_URL/profile/updatePersonalDetails.php"
    const val CHECK_EMAIL_AVAILABILITY_URL = "$BASE_URL/profile/checkEmailAvailability.php"
    const val UPDATE_USER_QR_CODE_URL = "$BASE_URL/profile/updateUserQrCode.php"

    /** calculation urls */
    const val GET_MEDICAMENT_URL = "$BASE_URL/calculation/getMedicament.php"
    const val GET_CALCULATIONS_HISTORY_URL = "$BASE_URL/calculation/getCalculationsHistory.php"
    const val GET_CALCULATION_BY_ID_URL = "$BASE_URL/calculation/getCalculationById.php"
    const val ADD_CALCULATION_HISTORY_URL = "$BASE_URL/calculation/addCalculationHistory.php"
    const val GET_USERS_URL = "$BASE_URL/users/getUsers.php"

    /** alert urls*/
    const val GET_NOTIFICATIONS_URL = "$BASE_URL/notifications/getNotifications.php"
    const val UPDATE_NOTIFICATION_URL = "$BASE_URL/notifications/updateNotification.php"
    const val SEND_NOTIFICATION_URL = "$BASE_URL/notifications/sendNotification.php"
}