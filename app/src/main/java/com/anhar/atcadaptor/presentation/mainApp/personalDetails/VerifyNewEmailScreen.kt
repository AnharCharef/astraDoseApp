package com.anhar.atcadaptor.presentation.mainApp.personalDetails

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Dimens
import com.anhar.atcadaptor.domain.model.user.User
import com.anhar.atcadaptor.presentation.authentication.forgotPassword.ForgotPasswordViewModel
import com.anhar.atcadaptor.presentation.components.OtpInputField
import kotlinx.coroutines.launch
import java.util.Locale

class VerifyNewEmailScreen(
    private var user : User,
    private val oldEmail : String,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var isOtpFilled by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        val navigator = LocalNavigator.current
        val isArabic = LocalContext.current.getSharedPreferences(Constant.APP_LANG, 0)
            .getString(Constant.APP_LANG, Locale.getDefault().language) == "ar"


        val forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()


        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }

        val currentState by forgotPasswordViewModel.forgotPasswordState

        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(currentState.verificationCodeError, currentState.verificationErrorMessage) {
            if (currentState.verificationCodeError != null && currentState.verificationCodeError != true) { // success

                navigator?.replace(PersonalDetailsScreen(user.copy(userEmail = oldEmail) , user.userEmail))
            } else if (currentState.verificationErrorMessage != null) {
                Toast.makeText(context, currentState.verificationErrorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
            forgotPasswordViewModel.resetState()
        }

        CompositionLocalProvider(
            if (!isArabic) {
                LocalLayoutDirection provides LayoutDirection.Ltr
            } else {
                LocalLayoutDirection provides LayoutDirection.Rtl
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
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
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navigator?.pop()
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
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.background)
                    )

                },

                ) { innerPadding ->

                Column(
                    modifier = Modifier
                        .padding(
                            bottom = innerPadding.calculateBottomPadding()
                        )
                        .fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.otpVerification),
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = innerPadding
                                    .calculateTopPadding()
                                    .plus(Dimens.LargePadding),
                                end = Dimens.LargePadding,
                                start = Dimens.LargePadding
                            ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(id = R.string.verificationBody) + user.userEmail,
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
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            OtpInputField(
                                modifier = Modifier
                                    .padding(top = 48.dp)
                                    .focusRequester(focusRequester),
                                otpText = currentState.verificationCode,
                                shouldCursorBlink = false,
                                onOtpModified = { value, otpFilled ->
                                    forgotPasswordViewModel.updateVerificationCode(value)
                                    isOtpFilled = otpFilled
                                    if (otpFilled) {
                                        keyboardController?.hide()
                                    }
                                }
                            )
                        }

                    }

                    Button(
                        onClick = {
                            scope.launch {
                                forgotPasswordViewModel.verifyCode(
                                    oldEmail,
                                    currentState.verificationCode
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Dimens.LargePadding,
                                vertical = Dimens.MediumPadding
                            ),
                        shape = RoundedCornerShape(30),
                        enabled = isOtpFilled
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
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