package com.anhar.atcadaptor.presentation.mainApp.calculationsHistory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomIcon
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.asking.AskingScreen
import com.anhar.atcadaptor.presentation.mainApp.calculationDetails.CalculationDetailsScreen
import com.anhar.atcadaptor.presentation.mainApp.components.EmptyContent
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen


class CalculationsHistoryScreen(private val patientUserId : String?=null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<CalculationsHistoryViewModel>()
        val state by viewModel.state
        val context = LocalContext.current
        val userId = context.getSharedPreferences(APP_ENTRY, 0).getString("userId", "0")
        val userType = context.getSharedPreferences(APP_ENTRY, 0).getString("userType", "0")


        LaunchedEffect(Unit) {
            if(patientUserId == null) {
                userId?.let { viewModel.getHistory(it) }
            }else{
                viewModel.getHistory(patientUserId)
            }
        }

        Scaffold(
            topBar = {
//                CustomTopAppBar(title = stringResource(R.string.calculationHistory), isArabic = false) {
//                    if (navigator.canPop) {
//                        navigator.pop()
//                    } else {
//                        navigator.replaceAll(HomeScreen())
//                    }
//                }

                CustomTopAppBar(
                    title = stringResource(R.string.calculationHistory),
                    isArabic = false,
                    actions = {
                        if(userType == "1") {
                            patientUserId?.let {
                                CustomIcon(imageVector = Icons.Default.Quiz) {
                                    navigator.push(AskingScreen(it))
                                }
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
                CustomCircularProgress(state.isLoading)
            } else if (state.historyList.isEmpty()) {
                EmptyContent(
                    alphaAnim = 4f,
                    iconId = R.drawable.ic_search_document,
                    message = "No Calculation Made Yet."
                ) {}
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(MediumPadding)
                        .padding(bottom = BottomBarHeight + SmallPadding)
                ) {
                    items(state.historyList.size) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = SmallPadding)
                                .clip(RoundedCornerShape(SmallPadding))
                                .clickable {
                                    userId?.let { id ->
                                        navigator.push(
                                            CalculationDetailsScreen(
                                                id,
                                                state.historyList[index].calculation_id.toString()
                                            )
                                        )
                                    }

                                },
                            shape = RoundedCornerShape(SmallPadding),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {

                            ListItem(headlineContent = {
                                Text(
                                    state.historyList[index].nom_de_medicament,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }, supportingContent = {
                                Text(
                                    "ID: ${state.historyList[index].calculation_id} | ${state.historyList[index].created_at}",
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