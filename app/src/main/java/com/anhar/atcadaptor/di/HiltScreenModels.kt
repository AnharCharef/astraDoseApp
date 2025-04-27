package com.anhar.atcadaptor.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.anhar.atcadaptor.presentation.authentication.forgotPassword.ForgotPasswordViewModel
import com.anhar.atcadaptor.presentation.authentication.login.LoginViewModel
import com.anhar.atcadaptor.presentation.authentication.signup.SignUpViewModel
import com.anhar.atcadaptor.presentation.mainApp.alert.AlertViewModel
import com.anhar.atcadaptor.presentation.mainApp.asking.AskingViewModel
import com.anhar.atcadaptor.presentation.mainApp.calculation.CalculationViewModel
import com.anhar.atcadaptor.presentation.mainApp.calculationDetails.CalculationDetailsViewModel
import com.anhar.atcadaptor.presentation.mainApp.calculationsHistory.CalculationsHistoryViewModel
import com.anhar.atcadaptor.presentation.mainApp.details.DetailsViewModel
import com.anhar.atcadaptor.presentation.mainApp.home.HomeViewModel
import com.anhar.atcadaptor.presentation.mainApp.patientDetails.PatientDetailsViewModel
import com.anhar.atcadaptor.presentation.mainApp.personalDetails.PersonalDetailsViewModel
import com.anhar.atcadaptor.presentation.mainApp.profile.ProfileScreenViewModel
import com.anhar.atcadaptor.presentation.mainApp.scanner.ScannerScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class HiltScreenModels {

    @Binds
    @IntoMap
    @ScreenModelKey(LoginViewModel::class)
    abstract fun bindHiltLoginViewModel(loginViewModel: LoginViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(HomeViewModel::class)
    abstract fun bindHiltHomeViewModel(homeViewModel: HomeViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(DetailsViewModel::class)
    abstract fun bindHiltDetailsViewModel(detailsViewModel: DetailsViewModel): ScreenModel


    @Binds
    @IntoMap
    @ScreenModelKey(CalculationViewModel::class)
    abstract fun bindHiltCalculationViewModel(calculationViewModel: CalculationViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(ForgotPasswordViewModel::class)
    abstract fun bindHiltForgotPasswordViewModel(forgotPasswordViewModel: ForgotPasswordViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(SignUpViewModel::class)
    abstract fun bindHiltSignUpViewModel(signUpViewModel: SignUpViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(ScannerScreenViewModel::class)
    abstract fun bindHiltScannerScreenViewModel(scannerScreenViewModel: ScannerScreenViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(ProfileScreenViewModel::class)
    abstract fun bindHiltProfileScreenViewModel(profileScreenViewModel: ProfileScreenViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(PersonalDetailsViewModel::class)
    abstract fun bindHiltPersonalDetailsViewModel(personalDetailsViewModel: PersonalDetailsViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(PatientDetailsViewModel::class)
    abstract fun bindHiltPatientDetailsViewModel(patientDetailsViewModel: PatientDetailsViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(CalculationDetailsViewModel::class)
    abstract fun bindHiltCalculationDetailsViewModel(calculationDetailsViewModel: CalculationDetailsViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(CalculationsHistoryViewModel::class)
    abstract fun bindHiltCalculationsHistoryViewModel(calculationsHistoryViewModel: CalculationsHistoryViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(AlertViewModel::class)
    abstract fun bindHiltAlertViewModel(alertViewModel: AlertViewModel): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(AskingViewModel::class)
    abstract fun bindHiltAskingViewModel(askingViewModel: AskingViewModel): ScreenModel

}