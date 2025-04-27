package com.anhar.atcadaptor.presentation.mainApp.calculation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant.format
import com.anhar.atcadaptor.common.DFGType
import com.anhar.atcadaptor.common.Genre
import com.anhar.atcadaptor.common.Race
import com.anhar.atcadaptor.common.Resource
import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import com.anhar.atcadaptor.domain.model.user.toUser
import com.anhar.atcadaptor.domain.useCases.calculation.CalculationUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalculationViewModel @Inject constructor(
    private val calculationUseCases: CalculationUseCases
) : ScreenModel {

    private val _inputState = mutableStateOf(
        CalculationInputState(
            nomDeMedicament = "Carboplatine",
            age = "65",
            poids = "78",
            taille = "170",
            genre = Genre.Homme,
            race = Race.Non_Afro_Americain,
            creatinine = "1.1",
            dfg = "75",
            dfgType = DFGType.MDRD,
            alat = "32",
            asat = "28",
            pal = "120",
            tbil = "1.0",
            doseParM2 = "250",
            aucCible = "6",
            toxiciteRenale = false,
            toxiciteHepatique = false,
            necessiteDialyse = false,
            sc = "1.95",
            dfgCalcule = "74.3",
            doseCarboplatine = "500"
        )
    )
    val inputState: State<CalculationInputState> = _inputState

    private val _state = mutableStateOf(CalculationState())
    val state: State<CalculationState> = _state

    private fun getMedicaments() {
        calculationUseCases.getMedicaments().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: result.data?.message ?: "An Unexpected Error"
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = null,
                        medicamentsList = result.data?.data ?: emptyList()
                    )
                }
            }

        }.launchIn(screenModelScope)
    }


    fun onInputEvent(event: CalculationInputEvent) {
        when (event) {
            is CalculationInputEvent.OnAgeChanged -> {
                val error = validateAge(event.value)
                _inputState.value =
                    inputState.value.copy(age = event.value , ageError = error)

            }

            is CalculationInputEvent.OnAlatChanged -> {
                val error = validateALAT(event.value)
                _inputState.value =
                    inputState.value.copy(alat = event.value, alatError = error)

            }

            is CalculationInputEvent.OnAsatChanged -> {
                val error = validateASAT(event.value)
                _inputState.value =
                    inputState.value.copy(asat = event.value,asatError = error)

            }

            is CalculationInputEvent.OnAucCibleChanged -> {
                val error = validateAUC(event.value)
                _inputState.value =
                    inputState.value.copy(aucCible = event.value,aucCibleError = error)

            }

            is CalculationInputEvent.OnCreatinineChanged -> {
                val error = validateCreatinine(event.value)
                _inputState.value =
                    inputState.value.copy(creatinine = event.value,creatinineError = error)


            }

            is CalculationInputEvent.OnDfgCalculeChanged -> {
                val error = validateDFG(event.value)
                _inputState.value =
                    inputState.value.copy(dfgCalcule = event.value,dfgCalculeError = error)

            }

            is CalculationInputEvent.OnDfgChanged -> {
                val error = validateDFG(event.value)
                _inputState.value =
                    inputState.value.copy(dfg = event.value, dfgError = error)

            }

            is CalculationInputEvent.OnDfgTypeChanged -> {
                _inputState.value = inputState.value.copy(
                    dfgType = event.value
                )
                onEvent(CalculationEvent.ToggleDfgTypeDropMenu)
            }

            is CalculationInputEvent.OnDoseCarboplatineChanged -> {
                val error = validateDose(event.value)
                _inputState.value =
                    inputState.value.copy(doseCarboplatine = event.value, doseCarboplatineError = error)

            }

            is CalculationInputEvent.OnDoseParM2Changed -> {
                val error = validateDose(event.value)
                _inputState.value =
                    inputState.value.copy(doseParM2 = event.value , doseParM2Error = error)

            }

            is CalculationInputEvent.OnGenreChanged -> {
                _inputState.value =
                    inputState.value.copy(genre = event.value)
                onEvent(CalculationEvent.ToggleGenreDropMenu)
            }

            is CalculationInputEvent.OnNecessiteDialyseChanged -> {
                _inputState.value = inputState.value.copy(necessiteDialyse = event.value)
                onEvent(CalculationEvent.ToggleDialyseDropMenu)
            }

            is CalculationInputEvent.OnNomDeMedicamentChanged -> {
                _inputState.value =
                    inputState.value.copy(nomDeMedicament = event.value)
                onEvent(CalculationEvent.ToggleNomDeMedicament)
            }

            is CalculationInputEvent.OnPalChanged -> {
                val error = validatePAL(event.value)
                _inputState.value =
                    inputState.value.copy(pal = event.value , palError = error)

            }

            is CalculationInputEvent.OnPoidsChanged -> {
                val error = validatePoids(event.value)
                _inputState.value =
                    inputState.value.copy(poids = event.value , poidsError = error)

            }

            is CalculationInputEvent.OnRaceChanged -> {
                _inputState.value = inputState.value.copy(race = event.value)
                onEvent(CalculationEvent.ToggleRaceDropMenu)
            }

            is CalculationInputEvent.OnScChanged -> {
                _inputState.value = inputState.value.copy(sc = event.value)
            }

            is CalculationInputEvent.OnTailleChanged -> {
                val error = validateTaille(event.value)
                _inputState.value =
                    inputState.value.copy(taille = event.value, tailleError = error)


            }

            is CalculationInputEvent.OnTbilChanged -> {
                val error = validateTBil(event.value)
                _inputState.value =
                    inputState.value.copy(tbil = event.value , tbilError = error)

            }

            is CalculationInputEvent.OnToxiciteHepatiqueChanged -> {
                _inputState.value = inputState.value.copy(toxiciteHepatique = event.value)
                onEvent(CalculationEvent.ToggleToxiciteHepatiqueDropMenu)
            }

            is CalculationInputEvent.OnToxiciteRenaleChanged -> {
                _inputState.value = inputState.value.copy(toxiciteRenale = event.value)
                onEvent(CalculationEvent.ToggleToxiciteRenaleDropMenu)
            }
        }
    }

    private fun validateAge(value: String): String? {
        return if (value.isNotEmpty() && value.matches(Regex("^\\d{1,3}$")) && value.toInt() in 0..120) {
            null // valid
        } else {
            "Âge invalide" // always show error until valid
        }
    }

    private fun validatePoids(value: String): String? {
        return if (value.matches(Regex("^\\d{1,3}(\\.\\d{0,2})?$"))) null else "Poids invalide"
    }

    private fun validateTaille(value: String): String? {
        return if (value.matches(Regex("^\\d{2,3}(\\.\\d{0,2})?$"))) null else "Taille invalide"
    }

    private fun validateDose(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "Dose invalide"
    }

    private fun validateAUC(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "AUC invalide"
    }

    private fun validateCreatinine(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "Créatinine invalide"
    }

    private fun validateDFG(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "DFG invalide"
    }

    private fun validateALAT(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "ALAT invalide"
    }

    private fun validateASAT(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "ASAT invalide"
    }

    private fun validatePAL(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "PAL invalide"
    }

    private fun validateTBil(value: String): String? {
        return if (value.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) null else "T.Bil invalide"
    }


    fun onEvent(event: CalculationEvent) {
        when (event) {
            is CalculationEvent.InitializeErrorMessage -> {
                _state.value = state.value.copy(error = null)
            }

            is CalculationEvent.OnCalculate -> {
                if (canCalculate()) {
                    _state.value = state.value.copy(error = null)
                    calculer()
                    onEvent(CalculationEvent.ToggleShowBottomSheet)
                } else {
                    _state.value = state.value.copy(
                        error = event.context.getString(R.string.pleaseFeelAllFeildsBeforeCalculating)
                    )
                    onEvent(CalculationEvent.ShowToast(event.context ,  event.context.getString(R.string.pleaseFeelAllFeildsBeforeCalculating)))
                }
            }

            CalculationEvent.ToggleDfgTypeDropMenu -> _state.value =
                state.value.copy(dropDfgTypeMenu = !state.value.dropDfgTypeMenu)

            CalculationEvent.ToggleDialyseDropMenu -> _state.value =
                state.value.copy(dropDialyseMenu = !state.value.dropDialyseMenu)

            CalculationEvent.ToggleGenreDropMenu -> _state.value =
                state.value.copy(dropGenreMenu = !state.value.dropGenreMenu)

            CalculationEvent.ToggleRaceDropMenu -> _state.value =
                state.value.copy(dropRaceMenu = !state.value.dropRaceMenu)

            CalculationEvent.ToggleToxiciteHepatiqueDropMenu -> _state.value =
                state.value.copy(dropToxiciteHepatiqueMenu = !state.value.dropToxiciteHepatiqueMenu)

            CalculationEvent.ToggleToxiciteRenaleDropMenu -> _state.value =
                state.value.copy(dropToxiciteRenaleMenu = !state.value.dropToxiciteRenaleMenu)

            CalculationEvent.ToggleShowBottomSheet -> _state.value =
                state.value.copy(showBottomSheet = !state.value.showBottomSheet)

            CalculationEvent.ToggleNomDeMedicament -> _state.value =
                state.value.copy(dropNomDeMedicamentMenu = !state.value.dropNomDeMedicamentMenu)

            CalculationEvent.GetMedicament -> {
                getMedicaments()
            }

            is CalculationEvent.OnCommentValueChange -> {
                _state.value = state.value.copy(
                    comment = event.value
                )
            }

            is CalculationEvent.SaveCalculation -> {
                if (state.value.selectedUser!=null){
                    saveCalculationToHistory(event.context, event.calculationData)
                }else{
                    onEvent(CalculationEvent.ShowToast(event.context , event.context.getString(R.string.pleaseSelectPatientErrorMsg)))
                }

            }

            is CalculationEvent.ShowToast -> {
                Toast.makeText(event.context, event.message, Toast.LENGTH_SHORT).show()
            }

            CalculationEvent.ToggleShowUserSelection -> {
                _state.value = state.value.copy(
                    showUserSelection = !state.value.showUserSelection
                )
            }

            is CalculationEvent.OnUserSearchQueryChange -> {
                updateSearchQuery(event.query)
            }

            is CalculationEvent.OnUserSelected -> {
                _state.value = state.value.copy(
                    selectedUser = event.user,
                    showUserSelection = false
                )
            }

            is CalculationEvent.OnSearch -> {
                search(event.query)
            }
        }


    }

    private var searchJob: Job? = null

    private fun updateSearchQuery(value: String) {
        _state.value = state.value.copy(
            userSearchQuery = value
        )

        val query = value.trim()

        searchJob?.cancel()
        searchJob = screenModelScope.launch {
            delay(500)
            if (query.isNotEmpty()) {
                search(query)
            } else {
                _state.value = state.value.copy(userSearchResults = emptyList())
            }
        }
    }

    fun search(searchQuery: String) {
        calculationUseCases.getUsersUseCase(searchQuery).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message,
                        userSearchResults = emptyList()
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = null,
                        userSearchResults = result.data?.users?.map { it.toUser() } ?: emptyList()
                    )
                }
            }
        }.launchIn(screenModelScope)
    }

    private fun saveCalculationToHistory(
        context: Context,
        calculationData: CalculationData
    ) {
        calculationUseCases.addCalculationHistoryUseCase(calculationData).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: result.data?.message
                    )
                    onEvent(
                        CalculationEvent.ShowToast(
                            context = context,
                            message = result.message ?: result.data?.message
                            ?: "An Unexpected Error"
                        )
                    )
                }

                is Resource.Successful -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = null
                    )

                    onEvent(
                        CalculationEvent.ShowToast(
                            context = context, message = context.getString(
                                R.string.calculationSaved
                            )
                        )
                    )
                    onEvent(CalculationEvent.InitializeErrorMessage)
                }
            }
        }.launchIn(screenModelScope)
    }

    private fun calculer() {
        val sc = calculerSC(inputState.value.poids.toDouble(), inputState.value.taille.toDouble())
        val dfg = calculerDFG(
            method = inputState.value.dfgType,
            race = inputState.value.race,
            creatinine = inputState.value.creatinine.toDouble(),
            age = inputState.value.age.toInt(),
            poids = inputState.value.poids.toDouble(),
            genre = inputState.value.genre
        )
        val doseCarboplatine = calculerDoseCarboplatine(inputState.value.aucCible.toDouble(), dfg)

        _inputState.value = inputState.value.copy(
            sc = sc.format(2),
            dfgCalcule = dfg.format(2),
            doseCarboplatine = doseCarboplatine.format(2)
        )

        val recommendationAndDose = getRecommendationAndDose(
            dfg = dfg,
            sc = sc,
            aucCible = inputState.value.aucCible.toDouble(),
            toxiciteRenale = inputState.value.toxiciteRenale,
            toxiciteHepatique = inputState.value.toxiciteHepatique,
            necessiteDialyse = inputState.value.necessiteDialyse
        )

        _state.value = state.value.copy(
            recommendationAndDose = recommendationAndDose
        )
    }

    private fun calculerSC(poids: Double, taille: Double): Double {
        return if (poids > 0 && taille > 0) {
            Math.sqrt((poids * taille) / 3600)
        } else 0.0
    }

    private fun calculerDFG(
        method: DFGType,
        age: Int,
        poids: Double,
        creatinine: Double,
        genre: Genre,
        race: Race,
    ): Double {
        return when (method) {
            DFGType.CG -> {
                val facteurSexe = if (genre == Genre.Femme) 0.85 else 1.0
                ((140 - age) * poids) / (72 * creatinine) * facteurSexe
            }

            DFGType.MDRD -> {
                val facteurSexe = if (genre == Genre.Femme) 0.742 else 1.0
                val facteurRace = if (race == Race.Afro_Americain) 1.212 else 1.0
                175 * Math.pow(creatinine, -1.154) * Math.pow(
                    age.toDouble(),
                    -0.203
                ) * facteurSexe * facteurRace
            }
        }
    }

    private fun calculerDoseCarboplatine(auc: Double, dfg: Double): Double {
        return if (auc > 0 && dfg > 0) auc * (dfg + 25) else 0.0
    }

    private fun canCalculate(): Boolean {
        val state = _inputState.value

        val requiredFieldsFilled =
            state.age.isNotBlank() && state.age != "0" &&
                    state.poids.isNotBlank() && state.poids != "0" &&
                    state.taille.isNotBlank() && state.taille != "0" &&
                    state.creatinine.isNotBlank() && state.creatinine != "0" &&
                    state.aucCible.isNotBlank() && state.aucCible != "0" &&
                    state.nomDeMedicament.isNotBlank()

        val noErrors =
            state.ageError == null &&
                    state.alatError == null &&
                    state.asatError == null &&
                    state.aucCibleError == null &&
                    state.creatinineError == null &&
                    state.dfgCalculeError == null &&
                    state.dfgError == null &&
                    state.doseCarboplatineError == null &&
                    state.doseParM2Error == null &&
                    state.nomDeMedicamentError == null &&
                    state.palError == null &&
                    state.poidsError == null &&
                    state.tailleError == null &&
                    state.tbilError == null &&
                    state.scError == null &&
                    _state.value.error == null

        return requiredFieldsFilled && noErrors
    }


    private fun getRecommendationAndDose(
        dfg: Double,
        sc: Double,
        aucCible: Double,
        toxiciteRenale: Boolean,
        toxiciteHepatique: Boolean,
        necessiteDialyse: Boolean,
    ): Pair<String, Double> {
        val recommendation: String
        var dose: Double

        if (necessiteDialyse) {
            recommendation = "not adapted"
            dose = 0.0
        } else if (dfg < 30 || toxiciteRenale) {
            recommendation = "not adapted"
            dose = 0.0
        } else {
            recommendation = "adapted"
            dose = aucCible * (dfg + 25) * sc

            if (toxiciteHepatique) {
                dose *= 0.8
            }
        }

        return Pair(recommendation, dose)
    }

}
