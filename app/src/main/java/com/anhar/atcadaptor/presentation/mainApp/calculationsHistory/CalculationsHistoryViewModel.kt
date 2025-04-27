package com.anhar.atcadaptor.presentation.mainApp.calculationsHistory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.useCases.calculation.CalculationUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CalculationsHistoryViewModel@Inject constructor(
    private val calculationsUseCases: CalculationUseCases
) : ScreenModel {

    private var _state = mutableStateOf(CalculationsHistoryState())
    val state : State<CalculationsHistoryState> = _state

    fun getHistory( userId : String ){
        calculationsUseCases.getCalculationsHistoryUseCase(userId).onEach {result->
            when(result){
                is Resource.Loading ->{
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error =  result.message?: result.data?.message ?: "An Unexpected Error"
                    )
                }

                is Resource.Successful ->{
                    _state.value = state.value.copy(
                        isLoading = false ,
                        error = null,
                        historyList = result.data?.data ?: emptyList()
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

}