package com.anhar.atcadaptor.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.domain.manager.LocaleUserEntryManager

class LocaleUserEntryManagerImpl(
    private val context : Context
) : LocaleUserEntryManager {

    private val appPref: SharedPreferences =
        context.getSharedPreferences(APP_ENTRY, Context.MODE_PRIVATE)
    override fun saveAppEntry(key : String , value : String) {
        val editor = appPref.edit()
        editor.putString(key , value)
        editor.apply()

    }

    override fun readAppEntry(key : String ,defaultValue : String): String {
        return appPref.getString(key , defaultValue) ?: "1"
    }
}