package com.anhar.atcadaptor.domain.manager

interface LocaleUserEntryManager {
    fun saveAppEntry(key : String , value : String)

    fun readAppEntry(key : String , defaultValue : String) : String
}