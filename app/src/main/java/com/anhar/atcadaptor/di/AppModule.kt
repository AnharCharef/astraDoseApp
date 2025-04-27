package com.anhar.atcadaptor.di

import android.app.Application
import com.anhar.atcadaptor.data.manager.LocaleUserEntryManagerImpl
import com.anhar.atcadaptor.data.remote.alert.AlertService
import com.anhar.atcadaptor.data.remote.auth.AuthService
import com.anhar.atcadaptor.data.remote.calculation.CalculationService
import com.anhar.atcadaptor.data.remote.details.DetailsService
import com.anhar.atcadaptor.data.remote.home.HomeService
import com.anhar.atcadaptor.data.remote.profile.ProfileService
import com.anhar.atcadaptor.data.repository.calculation.CalculationRepositoryImpl
import com.anhar.atcadaptor.data.repository.details.DetailsRepositoryImpl
import com.anhar.atcadaptor.data.repository.forgotPassword.ForgotPasswordRepositoryImpl
import com.anhar.atcadaptor.data.repository.home.HomeRepositoryImpl
import com.anhar.atcadaptor.data.repository.login.LoginRepositoryImpl
import com.anhar.atcadaptor.data.repository.notifications.NotificationsRepositoryImpl
import com.anhar.atcadaptor.data.repository.profile.ProfileRepositoryImpl
import com.anhar.atcadaptor.data.repository.signUp.SignUpRepositoryImpl
import com.anhar.atcadaptor.domain.manager.LocaleUserEntryManager
import com.anhar.atcadaptor.domain.repository.calculation.CalculationRepository
import com.anhar.atcadaptor.domain.repository.details.DetailsRepository
import com.anhar.atcadaptor.domain.repository.forgotPassword.ForgotPasswordRepository
import com.anhar.atcadaptor.domain.repository.home.HomeRepository
import com.anhar.atcadaptor.domain.repository.login.LoginRepository
import com.anhar.atcadaptor.domain.repository.notifications.NotificationsRepository
import com.anhar.atcadaptor.domain.repository.profile.ProfileRepository
import com.anhar.atcadaptor.domain.repository.signUp.SignUpRepository
import com.anhar.atcadaptor.domain.useCases.auth.CheckEmailUseCase
import com.anhar.atcadaptor.domain.useCases.auth.CheckPasswordUseCase
import com.anhar.atcadaptor.domain.useCases.appEntry.AppEntryUseCases
import com.anhar.atcadaptor.domain.useCases.appEntry.GetAppEntryUseCase
import com.anhar.atcadaptor.domain.useCases.login.LoginUseCase
import com.anhar.atcadaptor.domain.useCases.login.LoginUseCases
import com.anhar.atcadaptor.domain.useCases.appEntry.SaveAppEntryUseCase
import com.anhar.atcadaptor.domain.useCases.calculation.AddCalculationHistoryUseCase
import com.anhar.atcadaptor.domain.useCases.calculation.CalculationUseCases
import com.anhar.atcadaptor.domain.useCases.calculation.GetCalculationByIdUseCase
import com.anhar.atcadaptor.domain.useCases.calculation.GetCalculationsHistoryUseCase
import com.anhar.atcadaptor.domain.useCases.calculation.GetMedicamentUseCase
import com.anhar.atcadaptor.domain.useCases.calculation.GetUsersUseCase
import com.anhar.atcadaptor.domain.useCases.details.DetailsUseCases
import com.anhar.atcadaptor.domain.useCases.details.GetDetailsUseCase
import com.anhar.atcadaptor.domain.useCases.details.GetPatientDataUseCase
import com.anhar.atcadaptor.domain.useCases.forgotPassword.ForgotPasswordUseCases
import com.anhar.atcadaptor.domain.useCases.forgotPassword.ResetPasswordUseCase
import com.anhar.atcadaptor.domain.useCases.home.HomeSearchUseCase
import com.anhar.atcadaptor.domain.useCases.home.HomeUseCases
import com.anhar.atcadaptor.domain.useCases.notifications.GetNotificationsUseCase
import com.anhar.atcadaptor.domain.useCases.notifications.NotificationsUseCases
import com.anhar.atcadaptor.domain.useCases.notifications.SendNotificationUseCase
import com.anhar.atcadaptor.domain.useCases.notifications.UpdateNotificationUseCase
import com.anhar.atcadaptor.domain.useCases.profile.CheckEmailAvailabilityUseCase
import com.anhar.atcadaptor.domain.useCases.profile.GetUserDataUseCase
import com.anhar.atcadaptor.domain.useCases.profile.ProfileUseCases
import com.anhar.atcadaptor.domain.useCases.profile.SaveUserData
import com.anhar.atcadaptor.domain.useCases.profile.UpdatePersonalDetailsUseCase
import com.anhar.atcadaptor.domain.useCases.profile.UpdateUserQrCodeUseCase
import com.anhar.atcadaptor.domain.useCases.signUp.AddUserUseCase
import com.anhar.atcadaptor.domain.useCases.signUp.CheckPhoneUseCase
import com.anhar.atcadaptor.domain.useCases.signUp.CheckUserNameUseCase
import com.anhar.atcadaptor.domain.useCases.signUp.SignUpUseCases
import com.anhar.atcadaptor.domain.useCases.signUp.VerifyCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Ktor Client
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
        val client = HttpClient(CIO) {

            install(ContentNegotiation) {
                json(json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000L
                connectTimeoutMillis = 30_000L
                socketTimeoutMillis = 30_000L
            }


            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }


        }
        return client
    }

    @Provides
    @Singleton
    fun provideLocaleUserEntryManager(application: Application): LocaleUserEntryManager =
        LocaleUserEntryManagerImpl(application)

    @Provides
    @Singleton
    fun provideLocaleUserEntryUseCases(localeUserEntryManager: LocaleUserEntryManager): AppEntryUseCases =
        AppEntryUseCases(
            getAppEntryUseCase = GetAppEntryUseCase(localeUserEntryManager),
            saveAppEntryUseCase = SaveAppEntryUseCase(localeUserEntryManager)
        )

    @Provides
    @Singleton
    fun provideAuthService(client: HttpClient): AuthService = AuthService(client)


    // Login
    @Provides
    @Singleton
    fun provideLoginValidatorRepository(authService: AuthService): LoginRepository =
        LoginRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideLoginUseCases(
        loginRepository: LoginRepository,
        localeUserEntryManager: LocaleUserEntryManager? = null,
    ): LoginUseCases {
        return LoginUseCases(
            checkEmail = CheckEmailUseCase(loginRepository),
            checkPassword = CheckPasswordUseCase(loginRepository),
            login = LoginUseCase(loginRepository),
            saveAppEntry = if (localeUserEntryManager != null) SaveAppEntryUseCase(
                localeUserEntryManager
            ) else null
        )
    }


    // SignUp
    @Provides
    @Singleton
    fun provideSignUpRepository(authService: AuthService): SignUpRepository =
        SignUpRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideSignUpUseCases(signUpRepository: SignUpRepository): SignUpUseCases = SignUpUseCases(
        checkEmail = CheckEmailUseCase(signUpRepository = signUpRepository),
        checkPassword = CheckPasswordUseCase(signUpRepository = signUpRepository),
        checkUserName = CheckUserNameUseCase(signUpRepository = signUpRepository),
        checkPhone = CheckPhoneUseCase(signUpRepository = signUpRepository),
        addUser = AddUserUseCase(signUpRepository = signUpRepository),
        verifyCode = VerifyCodeUseCase(signUpRepository = signUpRepository)
    )


    // ForgotPassword
    @Provides
    @Singleton
    fun provideForgotPasswordRepository(authService: AuthService): ForgotPasswordRepository =
        ForgotPasswordRepositoryImpl(authService)

    @Provides
    @Singleton
    fun provideForgotPasswordUseCases(forgotPasswordRepository: ForgotPasswordRepository): ForgotPasswordUseCases =
        ForgotPasswordUseCases(
            checkEmail = CheckEmailUseCase(forgotPasswordRepository = forgotPasswordRepository),
            checkPassword = CheckPasswordUseCase(forgotPasswordRepository = forgotPasswordRepository),
            resetPassword = ResetPasswordUseCase(forgotPasswordRepository = forgotPasswordRepository),
            checkEmailDb = com.anhar.atcadaptor.domain.useCases.forgotPassword.CheckEmailUseCase(
                forgotPasswordRepository
            ),
            verifyCode = com.anhar.atcadaptor.domain.useCases.forgotPassword.VerifyCodeUseCase(
                forgotPasswordRepository
            )
        )

    // Home
    @Provides
    @Singleton
    fun provideHomeService(client: HttpClient): HomeService = HomeService(client)

    @Provides
    @Singleton
    fun provideHomeRepository(homeService: HomeService): HomeRepository =
        HomeRepositoryImpl(homeService)

    @Provides
    @Singleton
    fun provideHomeUseCases(
        homeRepository: HomeRepository,
        localeUserEntryManager: LocaleUserEntryManager? = null,
    ): HomeUseCases = HomeUseCases(
        search = HomeSearchUseCase(homeRepository),
        saveAppEntry = if (localeUserEntryManager != null) SaveAppEntryUseCase(
            localeUserEntryManager
        ) else null
    )

    // Details
    @Provides
    @Singleton
    fun provideDetailsService(client: HttpClient): DetailsService = DetailsService(client)

    @Provides
    @Singleton
    fun provideDetailsRepository(detailsService: DetailsService): DetailsRepository =
        DetailsRepositoryImpl(detailsService)

    @Provides
    @Singleton
    fun provideDetailsUseCases(detailsRepository: DetailsRepository): DetailsUseCases =
        DetailsUseCases(
            getDetailsUseCase = GetDetailsUseCase(detailsRepository),
            getPatientDataUseCase = GetPatientDataUseCase(detailsRepository)
        )


    // Profile
    @Provides
    @Singleton
    fun provideProfileService(client: HttpClient): ProfileService = ProfileService(client)

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileService: ProfileService,
        localeUserEntryManager: LocaleUserEntryManager,
    ): ProfileRepository =
        ProfileRepositoryImpl(profileService = profileService, localeUserEntryManager)

    @Provides
    @Singleton
    fun provideProfileUseCases(
        profileRepository: ProfileRepository
    ): ProfileUseCases = ProfileUseCases(
        saveUserData = SaveUserData(profileRepository),
        getUserData = GetUserDataUseCase(profileRepository),
        updatePersonalDetails = UpdatePersonalDetailsUseCase(profileRepository),
        checkEmail = CheckEmailUseCase(profileRepository = profileRepository),
        checkPassword = CheckPasswordUseCase(profileRepository = profileRepository),
        checkUserName = CheckUserNameUseCase(profileRepository = profileRepository),
        checkPhone = CheckPhoneUseCase(profileRepository = profileRepository),
        checkEmailAvailability = CheckEmailAvailabilityUseCase(profileRepository),
        updateUserQrCode = UpdateUserQrCodeUseCase(profileRepository),
    )


    // Calculation
    @Provides
    @Singleton
    fun provideCalculationService(client: HttpClient): CalculationService =
        CalculationService(client)

    @Provides
    @Singleton
    fun provideCalculationRepository(
        calculationService: CalculationService
    ): CalculationRepository = CalculationRepositoryImpl(calculationService)

    @Provides
    @Singleton
    fun provideCalculationUseCases(
        calculationRepository: CalculationRepository
    ): CalculationUseCases = CalculationUseCases(
        getMedicaments = GetMedicamentUseCase(calculationRepository),
        getCalculationByIdUseCase = GetCalculationByIdUseCase(calculationRepository),
        getCalculationsHistoryUseCase = GetCalculationsHistoryUseCase(calculationRepository),
        addCalculationHistoryUseCase = AddCalculationHistoryUseCase(calculationRepository),
        getUsersUseCase = GetUsersUseCase(calculationRepository)
    )


    // Alert

    @Provides
    @Singleton
    fun provideAlertService(client: HttpClient): AlertService =
        AlertService(client)

    @Provides
    @Singleton
    fun provideNotificationsRepository(service: AlertService): NotificationsRepository =
        NotificationsRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideNotificationsUseCases(notificationsRepository: NotificationsRepository): NotificationsUseCases =
        NotificationsUseCases(
            getNotifications = GetNotificationsUseCase(notificationsRepository) ,
            updateNotification = UpdateNotificationUseCase(notificationsRepository) ,
            sendNotification = SendNotificationUseCase(notificationsRepository)
        )
}