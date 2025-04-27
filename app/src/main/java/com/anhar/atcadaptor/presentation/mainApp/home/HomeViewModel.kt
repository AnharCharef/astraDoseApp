package com.anhar.atcadaptor.presentation.mainApp.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.home.HomeUseCases
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
) : ScreenModel {

    private var _state = mutableStateOf(HomeState(isLoading = false))
    val state: State<HomeState> = _state

    private var _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateSearchQuery -> {
                updateSearchQuery(event.value)
            }

            is HomeEvent.UpdateUserType -> {
                _state.value = state.value.copy(
                    userType = event.value
                )
            }

            is HomeEvent.Search -> {
                screenModelScope.launch {
                    search(event.value , state.value.userType)
                }
            }

            HomeEvent.ToggleNeedToBeLoggedBottomSheet -> {
                _state.value = state.value.copy(
                    needToBeLogged = !state.value.needToBeLogged
                )
            }

            is HomeEvent.SaveAppEntry -> {
                screenModelScope.launch {
                    homeUseCases.saveAppEntry?.let {
                        it(APP_ENTRY , "0")
                    }
                }
            }
        }
    }


    @OptIn(FlowPreview::class)
    private fun updateSearchQuery(value: String) {
        _searchQuery.value = value
        screenModelScope.launch {
            searchQuery.debounce(500).distinctUntilChanged().collectLatest { query ->
                Log.d("HomeViewModel", "updateSearchQuery: $query")
                if (query.isNotEmpty()) {
                    search(query, state.value.userType)
                }else {
                    _state.value = state.value.copy(
                        items = emptyList()
                    )
                }
            }
        }

    }


    suspend fun search(searchQuery: String , userType : Int) {
        homeUseCases.search(searchQuery, userType).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        errorMessage = null,
                        items = result.data?.data ?: emptyList()
                    )
                }
            }
        }.launchIn(screenModelScope)
    }
}