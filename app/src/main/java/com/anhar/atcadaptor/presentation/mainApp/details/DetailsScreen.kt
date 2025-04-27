package com.anhar.atcadaptor.presentation.mainApp.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.common.UserRole
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.details.components.LaboResultsInformationCard
import com.anhar.atcadaptor.presentation.mainApp.details.components.MedicationInformationCard
import com.anhar.atcadaptor.presentation.mainApp.details.components.PatientInformationCard
import com.anhar.atcadaptor.presentation.mainApp.patientDetails.PatientDetailsScreen

class DetailsScreen(
    private val userRole: UserRole,
    private val medicationName: String,
    private val patientId: Int?,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<DetailsViewModel>()
        val state by viewModel.state

        LaunchedEffect(Unit) {
            viewModel.onEvent(DetailsEvent.GetDetails(medicationName, patientId))
        }

        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = "Détails",
                    isArabic = false
                ) {
                    navigator.pop()
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (state.isLoading) {
                    CustomCircularProgress(state.isLoading)
                } else {
                    // Patient Information (Visible for Doctors Only)
                    if (userRole == UserRole.Doctor) {
                        PatientInformationCard(modifier = Modifier.padding(top = SmallPadding), state = state){
                            state.detailsData?.patientId?.let {
                                navigator.push(PatientDetailsScreen(it))
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Medication Details (Always Visible)
                   MedicationInformationCard(modifier = Modifier.padding(vertical = if (userRole !=UserRole.Doctor)SmallPadding else ExtraSmallPadding2), state = state)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Lab Results (Visible for Doctors Only)
                    if (userRole == UserRole.Doctor) {
                        LaboResultsInformationCard(modifier = Modifier.padding(bottom = SmallPadding), state = state)
                    }
                }
            }
        }
    }
}