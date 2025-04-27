package com.anhar.atcadaptor.data.repository.profile

import android.content.Context
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.data.remote.dto.StandardResponse
import com.anhar.atcadaptor.data.remote.profile.ProfileService
import com.anhar.atcadaptor.domain.manager.LocaleUserEntryManager
import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val profileService : ProfileService,
    private val localeUserEntryManager: LocaleUserEntryManager
) : ProfileRepository {
    override fun saveAppEntry(key: String, value: String) {
        return localeUserEntryManager.saveAppEntry(key, value)
    }


    override fun getUserInfo(key: String, defaultValue: String) : String {
        return localeUserEntryManager.readAppEntry(key, defaultValue)
    }

    override suspend fun updatePersonalDetails(
        userId: Int,
        name: String,
        email: String,
        phone: String,
        oldPassword: String,
        newPassword: String
    ): Flow<Resource<StandardResponse>> {
        return profileService.updatePersonalDetails(userId = userId,
            name = name,
            email = email,
            phone =phone,
            oldPassword = oldPassword,
            newPassword = newPassword
        )
    }

    override fun updateUserQrCode(userId : String, imageBase64: String): Flow<Resource<StandardResponse>> {
        return profileService.updateUserQrCode(userId = userId ,imageBase64 =imageBase64)
    }

    override fun checkUserName(userName: String, context: Context): Resource<Boolean> {
        return Constant.checkUserName(userName, context)
    }

    override fun checkEmail(email: String, context: Context): Resource<Boolean> {
        return Constant.checkEmail(email, context)
    }

    override fun checkPhone(phone: String, context: Context): Resource<Boolean> {
        return Constant.checkPhone(phone, context)
    }

    override fun checkPassword(password: String, context: Context): Resource<Boolean> {
        return Constant.checkPassword(password, context)
    }

    override suspend fun checkEmailAvailability(
        userId: Int,
        email: String
    ): Flow<Resource<StandardResponse>> {
       return profileService.checkEmailAvailability(userId , email)
    }
}