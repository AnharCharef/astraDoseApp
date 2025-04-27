package com.anhar.atcadaptor.presentation.mainApp.calculationDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.calculation.CalculationUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CalculationDetailsViewModel @Inject constructor(
    private val calculationUseCases: CalculationUseCases
):ScreenModel{

    private var _state = mutableStateOf(CalculationDetailsState())
    val state : State<CalculationDetailsState> = _state

    fun getCalculationDetails(userId : String,calculationId : String){
        calculationUseCases.getCalculationByIdUseCase(userId, calculationId).onEach { result->
            when(result){
                is Resource.Loading ->{
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = result.message ?: result.data?.message?:"An Unexpected Error"
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null ,
                        calculationData = result.data?.data
                    )
                }
            }
        }.launchIn(screenModelScope)
    }


}