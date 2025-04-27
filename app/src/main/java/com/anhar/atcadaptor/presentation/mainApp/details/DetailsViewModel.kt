package com.anhar.atcadaptor.presentation.mainApp.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.details.DetailsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val detailsUseCases: DetailsUseCases,
) : ScreenModel {

    private val _state = mutableStateOf(DetailsState(isLoading = true))
    val state: State<DetailsState> = _state

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.GetDetails -> {
                screenModelScope.launch {
                    getDetails(event.medicationName , event.patientId)
                }
            }
        }
    }

    private suspend fun getDetails(medicationName: String, patientId: Int? = null) {
        detailsUseCases.getDetailsUseCase(medicationName, patientId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = DetailsState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value =
                        DetailsState(
                            isLoading = false,
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )
                }

                is Resource.Successful -> {
                    _state.value = DetailsState(
                        isLoading = false,
                        detailsData = result.data?.data,
                        errorMessage = null
                    )
                }
            }

        }.launchIn(screenModelScope)
    }

}