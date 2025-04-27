package com.anhar.atcadaptor.presentation.mainApp

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.anhar.atcadaptor.common.Constant
import com.anhar.atcadaptor.common.Constant.APP_LANG
import com.anhar.atcadaptor.common.Constant.mainActivityBottomBarItems
import com.anhar.atcadaptor.common.Constant.setLocal
import com.anhar.atcadaptor.common.Dimens.BottomBarHeight
import com.anhar.atcadaptor.common.Dimens.MediumPadding
import com.anhar.atcadaptor.common.Dimens.SmallPadding
import com.anhar.atcadaptor.presentation.components.LeavingAppDialog
import com.anhar.atcadaptor.presentation.components.StatusBarColor
import com.anhar.atcadaptor.presentation.mainApp.alert.AlertScreen
import com.anhar.atcadaptor.presentation.mainApp.asking.AskingScreen
import com.anhar.atcadaptor.presentation.mainApp.calculation.CalculationScreen
import com.anhar.atcadaptor.presentation.mainApp.components.CustomBottomBar
import com.anhar.atcadaptor.presentation.mainApp.components.shadow
import com.anhar.atcadaptor.presentation.mainApp.home.HomeScreen
import com.anhar.atcadaptor.presentation.mainApp.profile.ProfileScreen
import com.anhar.atcadaptor.presentation.mainApp.scanner.ScannerScreen
import com.anhar.atcadaptor.presentation.ui.theme.ATCAdaptorTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var navigator: Navigator? = null
//    lateinit var viewModel : MainActivityViewModel
    private val currentLang by lazy {
        getSharedPreferences(APP_LANG, 0).getString(APP_LANG, Locale.getDefault().language)
            ?: Locale.getDefault().language
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocal(currentLang, this)


//        val userId = getSharedPreferences(Constant.APP_ENTRY, 0).getString("userId", "")
//        val userType = getSharedPreferences(Constant.APP_ENTRY, 0).getString("userType", "")
        val appEntry = getSharedPreferences(Constant.APP_ENTRY, 0).getString(Constant.APP_ENTRY, "")
            Log.d("AppEntry", "onCreate Main: $appEntry")


        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val state = viewModel.state.value

            var backPressedState by remember {
                mutableStateOf(false)
            }
            var showDialog by remember {
                mutableStateOf(false)
            }
            var showBottomBar by remember {
                mutableStateOf(true)
            }

            onBackButtonPressed {
                showDialog = !backPressedState
                if (navigator!!.canPop){
                    navigator!!.pop()
                }else {
                    if (navigator!!.lastItem.javaClass.name != HomeScreen::class.java.name) {
                        navigator!!.replace(HomeScreen())
                        showDialog = false
                    } else {
                        showDialog = true
                    }
                }
            }

            ATCAdaptorTheme {
                StatusBarColor()
                LaunchedEffect(navigator?.items) {
                    navigator?.let {
                        viewModel.apply {
                            when (navigator!!.lastItem::class.java.simpleName) {
                                HomeScreen::class.java.simpleName -> setCurrentScreen(2)
                                CalculationScreen::class.java.simpleName -> setCurrentScreen(0)
                                ScannerScreen::class.java.simpleName -> setCurrentScreen(3)
                                AlertScreen::class.java.simpleName -> setCurrentScreen(1)
                                ProfileScreen::class.java.simpleName -> setCurrentScreen(4)
                            }
                        }
                    }
                }
                Scaffold(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
                    bottomBar = {
                        if (showBottomBar) {
                            CustomBottomBar(
                                bottomBarItems = mainActivityBottomBarItems,
                                state.currentScreen,
                                modifier = Modifier.padding(
                                    start = MediumPadding,
                                    end = MediumPadding,
                                    bottom = SmallPadding
                                )
                            ) {
                                when (it) {
                                    2 -> {
                                        if (navigator?.lastItem?.javaClass?.name != HomeScreen::class.java.name)
                                            navigator?.replaceAll(HomeScreen())
                                    }

                                    0 -> {
                                        if (navigator?.lastItem?.javaClass?.name != CalculationScreen::class.java.name)
                                            if(navigator?.popUntil { screen -> screen ==  CalculationScreen() } == false)
                                                navigator?.replace(CalculationScreen())
                                    }

                                    3 -> {
                                        if (navigator?.lastItem?.javaClass?.name != ScannerScreen::class.java.name)
                                            if(navigator?.popUntil { screen -> screen ==  ScannerScreen() } == false)
                                                navigator?.replace(ScannerScreen())
                                    }

                                    1 -> {
                                        if (navigator?.lastItem?.javaClass?.name != AlertScreen::class.java.name)
                                            if(navigator?.popUntil { screen -> screen ==  AlertScreen() } == false)
                                                navigator?.replace(AlertScreen())
                                    }

                                    4 -> {
                                        if (navigator?.lastItem?.javaClass?.name != ProfileScreen::class.java.name)
                                            if(navigator?.popUntil { screen -> screen ==  ProfileScreen() } == false)
                                                navigator?.replace(ProfileScreen())

                                    }
                                }
                                viewModel.setCurrentScreen(it)
                            }
                        }
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Navigator(screen = HomeScreen()) { navigator ->
                            this@MainActivity.navigator = navigator
                            FadeTransition(navigator = navigator)
                            showBottomBar =
                                navigator.lastItem.javaClass.name != AskingScreen::class.java.name
//                                        navigator.lastItem.javaClass.name != CustomSizeScreen::class.java.name &&
//                                        navigator.lastItem.javaClass.name != CartScreen::class.java.name &&
//                                        navigator.lastItem.javaClass.name != CheckoutScreen::class.java.name &&
//                                        navigator.lastItem.javaClass.name != AddressScreen::class.java.name &&
//                                        navigator.lastItem.javaClass.name != PaymentScreen::class.java.name &&
//                                        navigator.lastItem.javaClass.name != OrderDetailsScreen::class.java.name &&
//                                        navigator.lastItem.javaClass.name != NotificationsScreen::class.java.name
                            backPressedState =
                                navigator.lastItem.javaClass.name != HomeScreen::class.java.name
                        }
                        if (showBottomBar) {
                            Spacer(
                                modifier = Modifier
                                    .height(BottomBarHeight)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .shadow(
                                        MaterialTheme.colorScheme.background,
                                        offsetY = BottomBarHeight,
                                        spread = MediumPadding * 2,
                                        blurRadius = (MediumPadding.value * 1.5).dp
                                    )
                            )
                        }
                    }

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


    private fun onBackButtonPressed(onBackPressed: () -> Unit) {
        onBackPressedDispatcher.addCallback(
            this@MainActivity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        setLocal(currentLang, this)
        super.onConfigurationChanged(newConfig)

    }





}