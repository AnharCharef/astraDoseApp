package com.anhar.atcadaptor.presentation.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.FadeTransition
import com.anhar.atcadaptor.presentation.authentication.login.LoginScreen
import com.anhar.atcadaptor.presentation.components.LeavingAppDialog
import com.anhar.atcadaptor.presentation.components.StatusBarColor
import com.anhar.atcadaptor.presentation.mainApp.MainActivity
import com.anhar.atcadaptor.presentation.ui.theme.ATCAdaptorTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = hiltViewModel<AuthActivityViewModel>()
            ATCAdaptorTheme {
                Log.d("AppEntry", "onCreate: ${viewModel.appEntry.value}")

                StatusBarColor()
                when (viewModel.appEntry.value) {
                    "2" -> { // with account
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }

                    "3" -> { // without account (visitor)
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }

                    else -> {

                        var backPressedState by remember {
                            mutableStateOf(false)
                        }
                        var showDialog by remember {
                            mutableStateOf(false)
                        }
                        onBackButtonPressed {
                            showDialog = !backPressedState
                            backPressedState
                        }


                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .windowInsetsPadding(WindowInsets.systemBars),
                        ) {

                            Navigator(
                                LoginScreen(),
                                disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false),
                            ) { navigator ->
                                FadeTransition(navigator = navigator)
                                backPressedState = navigator.canPop

                            }

                            LeavingAppDialog(
                                showDialog = showDialog,
                                onConfirmRequest = {
                                    finishAffinity()
                                    showDialog = false
                                },
                                onDismissRequest = {
                                    showDialog = false
                                }
                            )

                        }

                    }
                }
            }
        }
    }

    private fun onBackButtonPressed(callback: (() -> Boolean)) {
        onBackPressedDispatcher.addCallback(
            this@AuthActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (callback()) {
                        remove()
                        performBackPress()
                    }
                }
            })
    }

    fun performBackPress() {
        onBackPressedDispatcher.onBackPressed()
    }
}
