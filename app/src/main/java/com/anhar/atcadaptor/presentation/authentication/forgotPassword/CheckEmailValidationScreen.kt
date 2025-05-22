package com.anhar.atcadaptor.presentation.authentication.forgotPassword

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.common.Dimens.LargePadding
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.presentation.components.CustomTextField

import com.anhar.atcadaptor.presentation.authentication.verification.VerificationEmailForgotPasswordScreen
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import kotlinx.coroutines.launch
import java.util.Locale

class CheckEmailValidationScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val isArabic = LocalContext.current.getSharedPreferences(Constant.APP_LANG, 0)
            .getString(Constant.APP_LANG, Locale.getDefault().language) == "ar"

        val navigator = LocalNavigator.currentOrThrow

        val viewModel: ForgotPasswordViewModel = navigator.getNavigatorScreenModel()

        val state = viewModel.forgotPasswordState.value

        val context = LocalContext.current

        val scope = rememberCoroutineScope()

        LaunchedEffect(state.checkEmailError , state.checkEmailSuccessful) {
            if(state.checkEmailSuccessful){
                navigator.replace(
                    VerificationEmailForgotPasswordScreen(
                        forgotPasswordState = viewModel.forgotPasswordState.value
                    )
                )
            }else if (state.checkEmailError != null){
                Toast.makeText(context, state.checkEmailError, Toast.LENGTH_SHORT).show()
            }
            viewModel.resetState()

        }

        CompositionLocalProvider(
            if (!isArabic) {
                LocalLayoutDirection provides LayoutDirection.Ltr
            } else {
                LocalLayoutDirection provides LayoutDirection.Rtl
            }
        ) {
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        title = {
                        Text(
                            text = stringResource(id = R.string.forgotPassword),
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 24.sp),
                            color = Color.Gray
                        )
                    },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navigator.pop()
                                },
                            ) {
                                Image(
                                    Icons.Default.KeyboardArrowDown,
                                    colorFilter = ColorFilter.tint(color = Color.Gray),
                                    modifier = Modifier
                                        .rotate(if (isArabic) 90f else -90f)
                                        .size(Dimens.LargePadding),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
                                )
                            }
                        })

                }) { innerPadding ->
                val scrollState = rememberScrollState()
                if (state.isLoading) {
                    CustomCircularProgress(state.isLoading)
                } else {

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.LargePadding
                            )
                            .padding(
                                bottom = innerPadding.calculateBottomPadding()
                            )
                            .fillMaxSize()
                            .verticalScroll(state = scrollState, enabled = true)
                    ) {

                        Text(
                            text = stringResource(id = R.string.checkEmail),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = innerPadding
                                        .calculateTopPadding()
                                        .plus(LargePadding),
                                    end = Dimens.LargePadding,
                                    start = Dimens.LargePadding
                                ),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.forgotPasswordBodyText),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = Dimens.LargePadding,
                                    end = Dimens.LargePadding
                                ),
                            textAlign = TextAlign.Center
                        )

                        //Email
                        CustomTextField(
                            modifier = Modifier.padding(top = MediumPadding),
                            value = state.email,
                            label = stringResource(id = R.string.email),
                            placeholder = stringResource(id = R.string.enterYourEmail),
                            trailingIcon = Icons.Outlined.Email,
                            onValueChange = { value ->
                                viewModel.updateEmail(value)
                            },
                            isError = !state.emailError.isNullOrEmpty(),
                            isPassword = false,
                            errorMessage = state.emailError ?: ""
                        )
                        val context = LocalContext.current

                        Button(
                            onClick = {
                                if (
                                    viewModel.checkEmail(
                                        email = state.email, context
                                    )
                                ) {
                                    scope.launch {
                                        viewModel.checkEmail(state.email)
                                    }

                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = Dimens.LargePadding,
                                    vertical = Dimens.MediumPadding
                                ),
                            shape = RoundedCornerShape(30)
                        ) {
                            Text(
                                text = stringResource(id = R.string.continuee),
                                Modifier.padding(vertical = Dimens.ExtraSmallPadding),
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                    }
                }
            }

        }
    }
}