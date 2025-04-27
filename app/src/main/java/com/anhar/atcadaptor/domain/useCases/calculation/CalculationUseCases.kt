package com.anhar.atcadaptor.domain.useCases.calculation

data class CalculationUseCases(
    val getMedicaments : GetMedicamentUseCase ,
    val getCalculationByIdUseCase: GetCalculationByIdUseCase ,
    val getCalculationsHistoryUseCase: GetCalculationsHistoryUseCase ,
    val addCalculationHistoryUseCase: AddCalculationHistoryUseCase ,
    val getUsersUseCase : GetUsersUseCase
)
