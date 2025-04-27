package com.anhar.atcadaptor.presentation.mainApp.patientDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.details.DetailsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PatientDetailsViewModel @Inject constructor(
    private val detailsUseCases: DetailsUseCases
) : ScreenModel {

    private var _state = mutableStateOf(PatientDetailsState())
    val state: State<PatientDetailsState> = _state

    fun getDetails(patientId: Int ) {
        detailsUseCases.getPatientDataUseCase(patientId ,"" ,"" , "").onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: result.data?.message?: "An Unexpected Error"
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null ,
                        patientData = result.data?.patientData
                    )
                }
            }

        }.launchIn(screenModelScope)
    }

}