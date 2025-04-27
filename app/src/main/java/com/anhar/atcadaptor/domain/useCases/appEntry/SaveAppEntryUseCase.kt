package com.anhar.atcadaptor.domain.useCases.appEntry

import com.anhar.atcadaptor.domain.manager.LocaleUserEntryManager

class SaveAppEntryUseCase(
    private val localeUserEntryManager: LocaleUserEntryManager
) {
    operator fun invoke(key: String, value: String) {
        return localeUserEntryManager.saveAppEntry(key, value)
    }
}