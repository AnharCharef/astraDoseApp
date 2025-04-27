package com.anhar.atcadaptor.presentation.mainApp.calculationDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomIcon
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.asking.AskingScreen
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen
import com.anhar.atcadaptor.presentation.mainApp.patientDetails.InfoCard

class CalculationDetailsScreen(
    private val patientUserId: String,
    private val calculationId: String
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<CalculationDetailsViewModel>()
        val state by viewModel.state
        LaunchedEffect(Unit) {
            viewModel.getCalculationDetails(userId = patientUserId, calculationId = calculationId)
        }

        val context = LocalContext.current
        val userType = context.getSharedPreferences(APP_ENTRY ,  0).getString("userType" , "0")

        Scaffold(
            topBar = {
                CustomTopAppBar(title = "Calculation Details", isArabic = false,
                    actions = {
                        state.calculationData?.let {calculationData ->
                            CustomIcon(imageVector = Icons.Default.Quiz) {
                                navigator.push(
                                    AskingScreen(
                                        to = if (userType == "0")
                                            calculationData.calculated_by.toString()
                                        else
                                            patientUserId
                                    )
                                )
                            }
                        }

                    }) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replaceAll(HomeScreen())
                    }
                }

            }
        ) { innerPadding ->

            if (state.isLoading) {
                CustomCircularProgress(isLoading = true)
            } else {
                state.calculationData?.let { calculation ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(MediumPadding),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            InfoCard(
                                title = "Main Information",
                                subtitle = "Created on ${calculation.created_at ?: "-"}",
                                items = listOf(
                                    "Medication" to calculation.nom_de_medicament,
                                    "Review" to calculation.comment
                                )
                            )
                        }

                        item {
                            InfoCard(
                                title = "Patient Information",
                                items = listOf(
                                    "Age" to "${calculation.age} years",
                                    "Weight" to "${calculation.poids} kg",
                                    "Height" to "${calculation.taille} cm",
                                    "Gender" to calculation.genre,
                                    "Race" to calculation.race,
                                    "Requires Dialysis" to if (calculation.necessite_dialyse == 1) "Yes" else "No",
                                    "Kidney Toxicity" to if (calculation.toxicite_renale == 1) "Yes" else "No",
                                    "Hepatic Toxicity" to if (calculation.toxicite_hepatique == 1) "Yes" else "No"
                                )
                            )
                        }

                        item {
                            InfoCard(
                                title = "Lab Information",
                                items = listOf(
                                    "Creatinine" to calculation.creatinine,
                                    "ALAT" to calculation.alat,
                                    "ASAT" to calculation.asat,
                                    "PAL" to calculation.pal,
                                    "T.Bil" to calculation.tbil
                                )
                            )
                        }

                        item {
                            InfoCard(
                                modifier = Modifier.padding(bottom = BottomBarHeight + SmallPadding),
                                title = "Calculation & Recommendation",
                                items = listOf(
                                    "Body Surface (SC)" to "${calculation.sc} m²",
                                    "DFG" to calculation.dfg,
                                    "DFG Calculated" to calculation.dfg_calcule,
                                    "DFG Type" to calculation.dfg_type,
                                    "AUC Target" to calculation.auc_cible,
                                    "Carboplatin Dose" to "${calculation.dose_carboplatine} mg",
                                    "Recommended Dose" to "${calculation.dose_recommandee} mg",
                                    "Dose per m²" to "${calculation.dose_par_m2} mg/m²",
                                    "Recommendation" to calculation.recommandation
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}