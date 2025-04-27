package com.anhar.atcadaptor.presentation.mainApp.patientDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.calculationDetails.CalculationDetailsScreen
import com.anhar.atcadaptor.presentation.mainApp.calculationsHistory.CalculationsHistoryScreen
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen


class PatientDetailsScreen(
    private val patientId: Int
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<PatientDetailsViewModel>()
        val state by viewModel.state

        LaunchedEffect(Unit) {
            viewModel.getDetails(patientId)
        }

        Scaffold(
            topBar = {
                CustomTopAppBar(title = "Patient's details", isArabic = false) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replaceAll(HomeScreen())
                    }
                }

            }
        ) { innerPadding ->

            if (state.isLoading) {
                CustomCircularProgress(state.isLoading)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(MediumPadding)
                        .padding(bottom = BottomBarHeight + SmallPadding)
                ) {
                    item {
                        PatientSectionTitle("Personal Information")

                        InfoCard(
                            title = state.patientData?.userName ?: "",
                            subtitle = "ID: ${state.patientData?.userId ?: ""} | $patientId",
                            items = listOf(
                                Pair("📧 Email: ", state.patientData?.userEmail ?: ""),
                                Pair("📞 Phone: ", state.patientData?.userPhone ?: ""),
                                Pair("📅 Date Of Birth: ", state.patientData?.dateNaissance ?: ""),
                                Pair("🧬 Gender: ", state.patientData?.genre ?: ""),
                                Pair("🌍 Race: ", state.patientData?.race ?: "")
                            )
                        )
                        Spacer(modifier = Modifier.height(MediumPadding))
                    }


                    item {
                        PatientSectionTitle("Medical Information")
                        InfoCard(
                            title = "Measures",
                            items = listOf(
                                "⚖️ Weight: " to "${state.patientData?.poids} kg",
                                "📏 Height: " to "${state.patientData?.taille} cm"
                            )
                        )

                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PatientSectionTitle("Calculations Historic")
                            state.patientData?.let {patient->
                                if (patient.calculations.size > 3) {
                                    Text(
                                        "See All", style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            textDecoration = TextDecoration.Underline,
                                            color = colorResource(R.color.text_medium)
                                        ),
                                        modifier = Modifier.clickable {
                                            navigator.push(CalculationsHistoryScreen(patient.userId.toString()))
                                        }
                                    )
                                }
                            }
                        }

                    }
                    state.patientData?.let { patient ->
                        items(patient.calculations.size) { index ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = SmallPadding)
                                    .clip(RoundedCornerShape(SmallPadding))
                                    .clickable {
                                        navigator.push(
                                            CalculationDetailsScreen(
                                                patient.userId.toString(),
                                                patient.calculations[index].calculation_id.toString()
                                            )
                                        )

                                    },
                                shape = RoundedCornerShape(SmallPadding),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {

                                ListItem(headlineContent = {
                                    Text(
                                        patient.calculations[index].nom_de_medicament,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }, supportingContent = {
                                    Text(
                                        "ID: ${patient.calculations[index].calculation_id} | ${patient.calculations[index].created_at}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }, colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                )
                                )
                            }

                        }
                    }

                }
            }
        }

    }
}


@Composable
fun PatientSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = SmallPadding)
    )
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    items: List<Pair<String, String>>
) {
    Card(
        shape = RoundedCornerShape(SmallPadding),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(MediumPadding)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(SmallPadding))
            }

            items.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = label, fontWeight = FontWeight.Medium)
                    Text(text = value, textAlign = TextAlign.Start)
                }
            }
        }
    }
}

