package com.anhar.atcadaptor.presentation.mainApp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(

) : ViewModel() {

    private var _state = mutableStateOf(MainState())
    val state : State<MainState> = _state

    fun setCurrentScreen(value: Int) {
        _state.value = state.value.copy(currentScreen = value)
    }

}