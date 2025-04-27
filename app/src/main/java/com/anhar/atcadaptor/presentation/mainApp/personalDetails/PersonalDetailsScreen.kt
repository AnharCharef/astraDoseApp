package com.anhar.atcadaptor.presentation.mainApp.personalDetails

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.domain.model.user.User
import com.anhar.atcadaptor.presentation.components.CustomCircularProgress
import com.anhar.atcadaptor.presentation.components.CustomDialog
import com.anhar.atcadaptor.presentation.components.CustomTextField
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
//import com.youppix.atcadaptor.presentation.components.keyboardAsState
import java.util.Locale

data class PersonalDetailsScreen(var userData: User, val email: String? = null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: PersonalDetailsViewModel = navigator.getNavigatorScreenModel()
        val state by viewModel.state
        val scrollState = rememberScrollState()
        val context = LocalContext.current

        val focusManager = LocalFocusManager.current
//        val isKeyboardOpen by keyboardAsState()

//        LaunchedEffect(isKeyboardOpen ) {
//            if (!isKeyboardOpen) {
//                focusManager.clearFocus()
//            }
//        }

        val isArabic by remember {
            mutableStateOf(Locale.getDefault().language == "ar")
        }

        val density = LocalDensity.current
        val enterTransition = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        )

        LaunchedEffect(Unit) {
            email?.let { // if i have not check the email and verify it (come from the profile screen)
                viewModel.setUserState(
                    userData.copy(
                        userEmail = it
                    ),
                    it
                )
                viewModel.onEvent(
                    PersonalDetailsEvent.UpdatePersonalDetails(
                        context = context,
                        initialData = userData,
                        userId = state.user.userId,
                        name = state.user.userName,
                        email = state.user.userEmail,
                        phone = state.user.userPhone,
                        oldPassword = state.oldPassword,
                        newPassword = state.newPassword
                    )
                )
                navigator.pop()
                Toast.makeText(
                    context,
                    context.getString(R.string.updateSuccess),
                    Toast.LENGTH_SHORT
                ).show()
            } ?: run {
                viewModel.setUserState(userData)
            }

        }

        LaunchedEffect(state.updateSuccess) {
            if (state.updateSuccess) {
                viewModel.onEvent(PersonalDetailsEvent.SaveUserInformation(state.user))
                viewModel.onEvent(PersonalDetailsEvent.ToggleUpdatedSuccessState)
                navigator.pop()

                Toast.makeText(context, R.string.updateSuccess, Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(state.emailAvailable) {
            if (state.emailAvailable) {
                navigator.replace(VerifyNewEmailScreen(state.user, userData.userEmail))
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(
                    title = stringResource(id = R.string.personalDetails), isArabic = isArabic,
                    onSaveClick = {
                        viewModel.onEvent(
                            PersonalDetailsEvent.UpdatePersonalDetails(
                                context = context,
                                initialData = userData,
                                userId = state.user.userId,
                                name = state.user.userName,
                                email = state.user.userEmail,
                                phone = state.user.userPhone,
                                oldPassword = state.oldPassword,
                                newPassword = state.newPassword)
                        )
                    }
                ) {
                    navigator.pop()
                }
            })
        { innerPadding ->
            if (!state.isLoading) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .animateContentSize()
                        .verticalScroll(scrollState)
                ) {

                    //User Name
                    CustomTextField(
                        modifier = Modifier
                            .padding(top = MediumPadding, bottom = SmallPadding)
                            .padding(
                                horizontal = MediumPadding
                            ),
                        value = state.user.userName,
                        label = stringResource(id = R.string.userName),
                        placeholder = stringResource(id = R.string.enterYourUserName),
                        trailingIcon = Icons.Outlined.Person,
                        onValueChange = { value ->
                            viewModel.onEvent(PersonalDetailsEvent.UpdateUserName(value))
                        },
                        isError = !state.userNameError.isNullOrEmpty(),
                        errorMessage = state.userNameError ?: ""
                    )
                    //Email
                    CustomTextField(
                        modifier = Modifier
                            .padding(bottom = SmallPadding)
                            .padding(
                                horizontal = MediumPadding
                            ),
                        value = state.user.userEmail,
                        label = stringResource(id = R.string.email),
                        placeholder = stringResource(id = R.string.enterYourEmail),
                        trailingIcon = Icons.Outlined.Email,
                        onValueChange = { value ->
                            viewModel.onEvent(PersonalDetailsEvent.UpdateEmail(value))
                        },
                        isError = !state.emailError.isNullOrEmpty(),
                        errorMessage = state.emailError ?: ""
                    )
                    //Phone

                    CustomTextField(
                        modifier = Modifier
                            .padding(bottom = SmallPadding)
                            .padding(
                                horizontal = MediumPadding
                            ),
                        value = state.user.userPhone,
                        label = stringResource(id = R.string.phone),
                        placeholder = stringResource(id = R.string.enterYourPhone),
                        trailingIcon = Icons.Outlined.Phone,
                        onValueChange = { value ->
                            viewModel.onEvent(PersonalDetailsEvent.UpdatePhone(value))
                        },
                        isError = !state.phoneError.isNullOrEmpty(),
                        isPassword = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        errorMessage = state.phoneError ?: ""
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = SmallPadding)
                            .padding(horizontal = MediumPadding)
                            .clip(CircleShape)
                            .clickable {
                                viewModel.onEvent(PersonalDetailsEvent.ToggleEditPassword)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.changePassword),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .padding(horizontal = SmallPadding)
                                .background(color = MaterialTheme.colorScheme.primary)
                                .align(Alignment.CenterVertically)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.rotate(if (state.editPassword) 0f else 180f)
                        )
                    }

                    AnimatedVisibility(
                        state.editPassword, enter = enterTransition,
                        exit = slideOutVertically() + shrinkVertically() + fadeOut()
                    ) {
                        Column {


                            //Password
                            CustomTextField(
                                modifier = Modifier
                                    .padding(bottom = SmallPadding)
                                    .padding(
                                        horizontal = MediumPadding
                                    ),
                                value = state.oldPassword,
                                onValueChange = { value ->
                                    viewModel.onEvent(PersonalDetailsEvent.UpdatePassword(value))
                                },
                                label = stringResource(id = R.string.password),
                                placeholder = stringResource(id = R.string.enterYourPassword),
                                trailingIcon = Icons.Outlined.Lock,
                                isError = !state.oldPasswordError.isNullOrEmpty(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                isPassword = true,
                                showPassword = state.showPassword,
                                onShowPassword = {
                                    viewModel.onEvent(PersonalDetailsEvent.ToggleShowPassword)
                                },
                                errorMessage = state.oldPasswordError ?: ""
                            )

                            CustomTextField(
                                modifier = Modifier
                                    .padding(bottom = BottomBarHeight.plus(MediumPadding))
                                    .padding(
                                        horizontal = MediumPadding
                                    ),
                                value = state.newPassword,
                                onValueChange = { value ->
                                    viewModel.onEvent(PersonalDetailsEvent.UpdateNewPassword(value))
                                },
                                label = stringResource(id = R.string.newPassword),
                                placeholder = stringResource(id = R.string.enterYourNewPassword),
                                trailingIcon = Icons.Outlined.Lock,
                                isError = !state.newPasswordError.isNullOrEmpty(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                isPassword = true,
                                showPassword = state.showNewPassword,
                                onShowPassword = {
                                    viewModel.onEvent(PersonalDetailsEvent.ToggleShowNewPassword)
                                },
                                errorMessage = state.newPasswordError ?: ""
                            )
                        }
                    }


                }
            }

            CustomCircularProgress(isLoading = state.isLoading)

            CustomDialog(
                title = stringResource(id = R.string.error),
                message = state.updateError ?: "",
                showDialog = state.showDialog,
                onConfirmRequest = {
                    viewModel.onEvent(PersonalDetailsEvent.ToggleShowDialog)
                },
                onDismissRequest = {
                    viewModel.onEvent(PersonalDetailsEvent.ToggleShowDialog)
                })

            CustomDialog(
                title = stringResource(id = R.string.error),
                message = state.updateError ?: "",
                showDialog = state.emailErrorDialog,
                onConfirmRequest = {
                    viewModel.onEvent(
                        PersonalDetailsEvent.CheckEmailAvailability(
                            context = context,
                            userId = state.user.userId,
                            email = state.user.userEmail
                        )
                    )
                    viewModel.onEvent(PersonalDetailsEvent.ToggleShowEmailDialog)
                },
                onDismissRequest = {
                    viewModel.onEvent(PersonalDetailsEvent.ToggleShowEmailDialog)
                })


        }

    }
}