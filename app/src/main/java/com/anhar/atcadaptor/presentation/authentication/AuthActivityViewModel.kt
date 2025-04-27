package com.anhar.atcadaptor.presentation.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.domain.useCases.appEntry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthActivityViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {
    private var _appEntry = mutableStateOf("hello")
    val appEntry: State<String> = _appEntry

    init {
        _appEntry.value = appEntryUseCases.getAppEntryUseCase(APP_ENTRY, _appEntry.value)
    }
}