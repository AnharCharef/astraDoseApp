package com.anhar.atcadaptor.presentation.mainApp.calculation.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.common.Constant.format
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SearchBarHeight
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.domain.model.calculations.CalculationData
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomTextField
import com.anhar.atcadaptor.presentation.mainApp.calculation.CalculationEvent
import com.anhar.atcadaptor.presentation.mainApp.calculation.CalculationInputState
import com.anhar.atcadaptor.presentation.mainApp.calculation.CalculationState
import com.anhar.atcadaptor.presentation.mainApp.components.CustomSearchBar
import com.anhar.atcadaptor.presentation.mainApp.components.EmptyContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculationBottomSheet(
    modifier: Modifier = Modifier,
    inputState: CalculationInputState,
    state: CalculationState,
    event: (CalculationEvent) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = {
        it != SheetValue.Hidden
    })

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    val context = LocalContext.current
    val userId = context.getSharedPreferences(APP_ENTRY, 0).getString("userId", "")

    val scrollState = rememberScrollState()


    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .padding(top = SearchBarHeight),
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {


        Scaffold(topBar = {
            Text(
                text = stringResource(R.string.result),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
            bottomBar = {
                if (!state.showUserSelection) {
                    Column(Modifier.padding(bottom = SmallPadding)) {
                        Button(
                            onClick = {
                                inputState.apply {
                                    state.selectedUser?.let { selectedUser ->
                                        event(
                                            CalculationEvent.SaveCalculation(
                                                context, CalculationData(
                                                    user_id = selectedUser.userId,
                                                    calculated_by = if (userId.isNullOrEmpty()) 0 else userId.toInt(),
                                                    nom_de_medicament = nomDeMedicament,
                                                    age = age.toInt(),
                                                    poids = poids,
                                                    taille = taille,
                                                    genre = genre.name,
                                                    race = race.name,
                                                    creatinine = creatinine,
                                                    alat = alat,
                                                    asat = asat,
                                                    pal = pal,
                                                    tbil = tbil,
                                                    dfg_type = dfgType.name,
                                                    dfg = dfg,
                                                    dfg_calcule = dfgCalcule,
                                                    auc_cible = aucCible,
                                                    dose_carboplatine = doseCarboplatine,
                                                    dose_par_m2 = doseParM2,
                                                    toxicite_renale = if (toxiciteRenale) 1 else 0,
                                                    toxicite_hepatique = if (toxiciteHepatique) 1 else 0,
                                                    necessite_dialyse = if (necessiteDialyse) 1 else 0,
                                                    sc = sc,
                                                    recommandation = state.recommendationAndDose.first,
                                                    dose_recommandee = state.recommendationAndDose.second.toString(),
                                                    comment = state.comment
                                                )
                                            )
                                        )
                                    }
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MediumPadding, vertical = SmallPadding)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = stringResource(R.string.save),
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = { onDismissRequest() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MediumPadding)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = stringResource(R.string.close),
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }

                    }
                }
            })
        { innerPadding ->

            if (state.showUserSelection) {

                Scaffold(modifier = Modifier.fillMaxSize().padding(innerPadding)
                    .padding(bottom = SmallPadding), topBar = {
                    CustomSearchBar(value = state.userSearchQuery,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( SmallPadding),
                        hint = stringResource(R.string.search),
                        onTextCleared = {
                            event(CalculationEvent.OnUserSearchQueryChange(""))
                        },
                        onSearchClicked = {},
                        onTextChange = {
                            event(CalculationEvent.OnUserSearchQueryChange(it))
                        },
                        isEnabled = true,
                        onBoxCLicked = {})
                }, bottomBar = {
                    Button(
                        onClick = { event(CalculationEvent.ToggleShowUserSelection) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MediumPadding)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }) { innerPadding2 ->

                    if (state.isLoading) {
                        CustomCircularProgress(state.isLoading)
                    } else if (state.userSearchResults.isEmpty()){
                        EmptyContent(alphaAnim = 0.1f, iconId =  R.drawable.ic_search_document,message = "Invalid User.") {}
                    } else {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding2)

                        ) {
                            LazyColumn(
                                modifier.padding(
                                    vertical = SmallPadding
                                )
                            ) {
                                items(state.userSearchResults.size, key = { it }) { index ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(SmallPadding)
                                            .clip(RoundedCornerShape(SmallPadding))
                                            .clickable {
                                                event(
                                                    CalculationEvent.OnUserSelected(
                                                        state.userSearchResults[index]
                                                    )
                                                )
                                            },
                                        shape = RoundedCornerShape(SmallPadding),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White)
                                    ) {

                                        ListItem(headlineContent = {
                                            Text(
                                                state.userSearchResults[index].userName,
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                        }, supportingContent = {
                                            Text(
                                                "ID: ${state.userSearchResults[index].userId} | ${state.userSearchResults[index].userEmail}",
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


            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(SmallPadding)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if(state.isLoading){

                    }else {
                        AnimatedVisibility(!imeVisible) {
                            Card(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(ExtraSmallPadding),
                                shape = RoundedCornerShape(SmallPadding),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                val (recommendation, recommendedDose) = state.recommendationAndDose
                                Log.d("CalcultaionBottomSheet", "recommendation :" + recommendation)
                                Column(
                                    modifier = Modifier.padding(MediumPadding)
                                ) {
                                    ResultRow(
                                        label = "Surface corporelle (SC)",
                                        value = "${inputState.sc.format(2)} m²"
                                    )
                                    ResultRow(
                                        label = "DFG calculé",
                                        value = "${inputState.dfgCalcule.format(2)} mL/min"
                                    )
                                    ResultRow(
                                        label = "Dose de carboplatine",
                                        value = "${inputState.doseCarboplatine.format(2)} mg"
                                    )
                                    ResultRow(
                                        label = "Recommandation selon la ligne directrice (RCP) :",
                                        value = recommendation
                                    )
                                    ResultRow(
                                        label = "Dose individuelle recommandée: ",
                                        value = "${recommendedDose.format(2)} mg"
                                    )
                                }
                            }
                        }


                        CustomTextField(
                            modifier = Modifier.padding(
                                top = SmallPadding,
                                end = SmallPadding,
                                start = SmallPadding
                            ),
                            value = state.comment,
                            onValueChange = {
                                event(CalculationEvent.OnCommentValueChange(it))
                            },
                            label = stringResource(R.string.review),
                            trailingIcon = {},
                            placeholder = stringResource(R.string.leaveReview),
                            isError = false,
                            errorMessage = "",
                            maxLines = Int.MAX_VALUE
                        )
                        TextButton(
                            modifier = Modifier.padding(horizontal = SmallPadding),
                            onClick = {
                                event(CalculationEvent.ToggleShowUserSelection)
                            }) {
                            Text(
                                stringResource(R.string.selectPatient),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    textDecoration = TextDecoration.Underline,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        if (state.selectedUser != null) {
                            Card(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = SmallPadding,
                                        end = SmallPadding,
                                        bottom = SmallPadding,
                                    ),
                                shape = RoundedCornerShape(SmallPadding),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {

                                ListItem(headlineContent = {
                                    Text(
                                        state.selectedUser.userName,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }, supportingContent = {
                                    Text(
                                        "ID: ${state.selectedUser.userId} | ${state.selectedUser.userEmail}",
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResultRow(label: String, value: String) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium, fontSize = 16.sp)
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )
    }
}

