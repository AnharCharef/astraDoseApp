package com.anhar.atcadaptor.presentation.mainApp.profile

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.anhar.atcadaptor.R
import com.anhar.atcadaptor.common.Constant.APP_ENTRY
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.ExtraSmallPadding2
import com.anhar.atcadaptor.common.Dimens.LargePadding
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.authentication.AuthActivity
import com.anhar.atcadaptor.presentation.components.CustomDialog
import com.anhar.atcadaptor.presentation.components.CustomTopAppBar
import com.anhar.atcadaptor.presentation.components.NotificationPermissionHandler
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen
import com.anhar.atcadaptor.presentation.mainApp.profile.components.ProfileItem
import com.anhar.atcadaptor.presentation.mainApp.personalDetails.PersonalDetailsScreen
import com.anhar.atcadaptor.presentation.mainApp.profile.components.ContactezScreen
import com.anhar.atcadaptor.presentation.mainApp.profile.components.ImageSection
import com.anhar.atcadaptor.presentation.mainApp.profile.components.PolitiqueScreen
import java.util.Locale


class ProfileScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ProfileScreenViewModel = navigator.getNavigatorScreenModel()
        val state by viewModel.state
        val context = LocalContext.current
        val isArabic = remember {
            Locale.getDefault().language == "ar"
        }

        LaunchedEffect(Unit) {
            viewModel.onEvent(ProfileEvent.GetUserData(context))
        }
        LaunchedEffect(state.error) {
            if (!state.error.isNullOrEmpty()) {
                Toast.makeText(
                    context,
                    context.getString(R.string.error) + ": ${state.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        NotificationPermissionHandler(
            isNotificationEnable = state.isNotificationEnable,
            onPermissionDenied = {
                // If permission is denied, toggle the switch back off
                viewModel.onEvent(ProfileEvent.ToggleNotification(false))
            },
            onPermissionGranted = {
                // Permission granted, proceed with enabling notifications
                viewModel.onEvent(ProfileEvent.ToggleNotification(true))
            }
        )

        val appEntry = LocalContext.current.getSharedPreferences(APP_ENTRY, 0)
            .getString(APP_ENTRY, "")




        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(
                    title = stringResource(id = R.string.profile), isArabic = isArabic
                ) {
                    if (navigator.canPop) {
                        navigator.pop()
                    } else {
                        navigator.replace(HomeScreen())
                    }
                }
            },
        ) { innerPadding ->

            if (appEntry == "3") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .padding(horizontal = MediumPadding, vertical = SmallPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Illustration
                    Image(
                        imageVector = Icons.Default.Accessibility, // Remplacez avec votre ressource
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = MediumPadding)
                    )

                    // Titre
                    Text(
                        text = "An account is required",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(SmallPadding))

                    // Sous-titre
                    Text(
                        text = "Create an account or log in to access this feature.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = colorResource(R.color.body)
                        ),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(LargePadding))

                    Button(
                        onClick = {
                            viewModel.saveAppEntry("0")
                            context.startActivity(Intent(context , AuthActivity::class.java))
                            (context as Activity).finishAffinity()
                        },
                        modifier = Modifier.padding(horizontal = MediumPadding),
                        shape = CircleShape
                    ) {
                        Text(
                            "Login Or Signup", style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            } else {

//                if (state.isLoading) {
//                    CustomCircularProgress(state.isLoading)
//                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = MediumPadding, vertical = SmallPadding)
                            .animateContentSize(),
                    ) {

                        item {

                            ImageSection(
                                userQrImageBase64 = state.user.userImage,
                                userName = state.user.userName
                            ) {
                                viewModel.onEvent(ProfileEvent.UpdateUserQrCode)
                            }

                        }

                        item {
                            Column(
                                Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
                            ) {
                                ProfileItem(
                                    modifier = Modifier.padding(vertical = ExtraSmallPadding2),
                                    isArabic = isArabic,
                                    painter = painterResource(id = R.drawable.ic_person),
                                    title = stringResource(id = R.string.personalDetails)
                                ) {
                                    navigator.push(PersonalDetailsScreen(state.user))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(0.5.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = SmallPadding)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                )
                            }
                        }
                        item {
                            Column(
                                Modifier
                                    .fillMaxWidth(), verticalArrangement = Arrangement.Center
                            ) {
                                ProfileItem(
                                    modifier = Modifier.padding(vertical = ExtraSmallPadding2),
                                    checked = state.isNotificationEnable,
                                    imageVector = Icons.Outlined.Notifications,
                                    title = stringResource(id = R.string.notifications),
                                    onLongClick = {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            val intent =
                                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                                    .putExtra(
                                                        Settings.EXTRA_APP_PACKAGE,
                                                        context.packageName
                                                    )
                                            startActivity(context, intent, null)
                                        }
                                    }
                                ) {
                                    if (state.isNotificationEnable)
                                        viewModel.onEvent(ProfileEvent.ToggleNotification(false))
                                    else
                                        viewModel.onEvent(ProfileEvent.ToggleNotification(true))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(0.5.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = SmallPadding)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                )
                            }
                        }

                        item {
                            Column(
                                Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
                            ) {
                                ProfileItem(
                                    modifier = Modifier.padding(vertical = ExtraSmallPadding2),
                                    isArabic = isArabic,
                                    painter = painterResource(id = R.drawable.ic_security),
                                    title = stringResource(id = R.string.privacyPolicy)
                                ) {
                                    navigator.push(PolitiqueScreen())
                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(0.5.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = SmallPadding)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                )
                            }
                        }

//                item {
//                    ChangeLangSection(
//                        isArabic = isArabic,
//                        dropLanguageMenu = dropLanguageMenu,
//                        onDismissRequest = { dropLanguageMenu = false },
//                        onCLick = { dropLanguageMenu = !dropLanguageMenu },
//                        event = viewModel::onEvent
//                    )
//                }

                        item {
                            Column(
                                Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
                            ) {
                                ProfileItem(
                                    modifier = Modifier.padding(vertical = ExtraSmallPadding2),
                                    isArabic = isArabic,
                                    imageVector = Icons.AutoMirrored.Outlined.Message,
                                    title = stringResource(id = R.string.contactUs)
                                ) {
                                    navigator.push(ContactezScreen())
                                }
                                Spacer(
                                    modifier = Modifier
                                        .height(0.5.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = SmallPadding)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                )
                            }
                        }

                        item {
                            ProfileItem(
                                modifier = Modifier
                                    .padding(vertical = ExtraSmallPadding2)
                                    .padding(bottom = BottomBarHeight.plus(MediumPadding * 2)),
                                isArabic = isArabic,
                                painter = painterResource(id = R.drawable.ic_logout),
                                title = stringResource(id = R.string.logout)
                            ) {
                                viewModel.onEvent(ProfileEvent.ShowDialog)
                            }
                        }

                    }

                    CustomDialog(title = stringResource(id = R.string.logout),
                        message = stringResource(id = R.string.logoutMessage),
                        showDialog = state.showDialog,
                        onConfirmRequest = {
                            viewModel.onEvent(ProfileEvent.Logout)
                            context.startActivity(Intent(context, AuthActivity::class.java))
                        },
                        onDismissRequest = {
                            viewModel.onEvent(ProfileEvent.HideDialog)
                        })

                }
            }
//        }
    }
}