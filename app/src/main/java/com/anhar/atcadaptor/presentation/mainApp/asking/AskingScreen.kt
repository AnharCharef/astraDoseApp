package com.anhar.atcadaptor.presentation.mainApp.asking

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomTextField
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen

class AskingScreen(val to : String ) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: AskingViewModel = navigator.getNavigatorScreenModel()
        val state by viewModel.state


        val context = LocalContext.current

        val userType = context.getSharedPreferences(Constant.APP_ENTRY, 0)
            .getString("userType", "0")
        val userId = context.getSharedPreferences(Constant.APP_ENTRY, 0)
            .getString("userId", "0")

        LaunchedEffect(key1 = state.error) {

            if(!state.error.isNullOrEmpty()){
                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(state.success) {
            if(state.success){
                Toast.makeText(context, "Sent Successfully", Toast.LENGTH_SHORT).show()
                navigator.pop()
                viewModel.onEvent(AskingEvent.ResetState)
            }
        }

        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = if (userType == "0") "Ask The Doctor" else "Send Notice",
                    isArabic = false
                ) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replace(HomeScreen())
                    }
                }
            } ,
            bottomBar = {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumPadding) , onClick = {
                    userId?.let {
                        viewModel.onEvent(
                            AskingEvent.SendNotification(
                                topic = to,
                                title = state.objectValue,
                                body = state.askingValue,
                                sender = it
                            )
                        )
                    }
                }) {
                    Text("Send" , style = MaterialTheme.typography.titleSmall)
                }
            }
        ) { innerPadding ->

            if (state.isLoading) {
                CustomCircularProgress(state.isLoading)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CustomTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MediumPadding),
                        value = state.objectValue,
                        onValueChange = {
                            viewModel.onEvent(AskingEvent.OnObjectValueChange(it))
                        },
                        label = "Object",
                        placeholder = "Enter The Object Of Your Request",
                        trailingIcon = {},
                        isError = false,
                        errorMessage = ""
                    )

                    CustomTextField(
                        modifier = Modifier.padding(horizontal = MediumPadding),
                        value = state.askingValue,
                        onValueChange = {
                            viewModel.onEvent(AskingEvent.OnAskValueChange(it))
                        },
                        label = if (userType == "1") stringResource(R.string.review) else "Question",
                        trailingIcon = {},
                        placeholder = if (userType == "1") stringResource(R.string.leaveReview) else "Leave your question here ",
                        isError = false,
                        errorMessage = "",
                        maxLines = Int.MAX_VALUE
                    )
                }
            }
        }
    }
}