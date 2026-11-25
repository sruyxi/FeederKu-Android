package com.example.feederku.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feederku.views.auth.LoginRoute
import com.example.feederku.views.auth.SignUpRoute
import com.example.feederku.views.auth.WelcomeScreen
import com.example.feederku.views.auth.ForgotPasswordScreen
import com.example.feederku.views.auth.AuthViewModel
import com.example.feederku.views.community.ChatRoute
import com.example.feederku.views.dashboard.DashboardRoute
import com.example.feederku.views.dashboard.SeeAllStreamsScreen
import com.example.feederku.views.donation.DonationDetailsScreen
import com.example.feederku.views.donation.DonationPaymentMethodScreen
import com.example.feederku.views.donation.DonationPaymentScreen
import com.example.feederku.views.donation.DonationProcessingScreen
import com.example.feederku.views.donation.DonationRegistrationScreen
import com.example.feederku.views.donation.DonationRoute
import com.example.feederku.views.donation.DonationSuccessScreen
import com.example.feederku.views.donation.DonationViewModel
import com.example.feederku.views.livestream.LiveStreamRoute
import com.example.feederku.views.map.MapViewModel
import com.example.feederku.views.map.pages.LocationDetailsScreen
import com.example.feederku.views.map.pages.MapScreen
import com.example.feederku.views.onboarding.OnBoardingRoute
import com.example.feederku.views.profile.ProfileScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = if (FirebaseAuth.getInstance().currentUser != null) {
        Routes.Dashboard.route
    } else {
        Routes.OnBoarding.route
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.OnBoarding.route) {
            OnBoardingRoute(
                onFinish = {
                    navController.navigate(Routes.Welcome.route) {
                        popUpTo(Routes.OnBoarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Welcome.route){
            WelcomeScreen(
                onSignUpClick = {
                    navController.navigate(Routes.SignUp.route)
                },
                onLoginClick = {
                    navController.navigate(Routes.Login.route)
                }
            )
        }

        composable(Routes.SignUp.route) {
            SignUpRoute(
                onNavigateToLogin= {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Welcome.route) { inclusive = true }
                    }
                },
                onLoginSuccess = { navController.goToDashboard() },
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Routes.Login.route){
                        popUpTo(Routes.SignUp.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Login.route) {
            LoginRoute(
                onLoginSuccess = { navController.goToDashboard() },
                onBackClick = { navController.goBack() },
                onSignUpClick = {
                    navController.navigate(Routes.SignUp.route){
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate(Routes.ForgotPassword.route)
                }
            )
        }

        composable(Routes.ForgotPassword.route) {
            val viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            val uiState by viewModel.uiState.collectAsState()
            ForgotPasswordScreen(
                uiState = uiState,
                onEmailChange = viewModel::onEmailChange,
                onSendResetLinkClick = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(Routes.Dashboard.route){
            DashboardRoute(
                onProfileClick = {
                    navController.navigate(Routes.Profile.route)
                },
                onGoLiveClick = {
                    navController.navigate(Routes.LiveStreamFlow.route)
                },
                onLiveStreamClick = { stream ->
                    navController.navigate(Routes.LiveStreamFlow.route)
                },
                onSeeAllClick = {
                    navController.navigate(Routes.SeeAllStreams.route)
                },
                onNavigateToMap = {
                    navController.navigate(Routes.Map.route)
                },
                onNavigateToDonation = {
                    navController.navigate(Routes.Donation.route)
                },
                onNavigateToChat = {
                    navController.navigate(Routes.Chat.route)
                }
            )
        }

        composable(Routes.SeeAllStreams.route) {
            SeeAllStreamsScreen(
                onBackClick = { navController.popBackStack() },
                onStreamClick = { streamId ->
                    navController.navigate(Routes.LiveStreamFlow.route)
                }
            )
        }

        composable(Routes.Map.route) {
            val mapViewModel: MapViewModel = viewModel()

            MapScreen(
                viewModel = mapViewModel,
                onNavigateToDetails = { id: String ->
                    navController.navigate(Routes.LocationDetails.createRoute(id))
                },
                onNavigateToHome = {
                    navController.navigate(Routes.Dashboard.route) {
                        popUpTo(Routes.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToDonation = {
                    navController.navigate(Routes.Donation.route)
                },
                onNavigateToChat = {
                    navController.navigate(Routes.Chat.route)
                }
            )
        }

        composable(Routes.LocationDetails.route) { backStackEntry ->
            val locationId = backStackEntry.arguments?.getString("locationId").orEmpty()
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Map.route)
            }
            val mapViewModel: MapViewModel = viewModel(parentEntry)
            val mapUiState by mapViewModel.uiState.collectAsState()

            val selectedSighting = remember(locationId, mapUiState.sightings) {
                mapViewModel.getSightingById(locationId)
            }

            LocationDetailsScreen(
                locationId = locationId,
                sighting = selectedSighting,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.Chat.route) {
            ChatRoute(
                onNavigateToHome = { navController.navigate(Routes.Dashboard.route) { popUpTo(Routes.Dashboard.route) { inclusive = true } } },
                onNavigateToMap = { navController.navigate(Routes.Map.route) },
                onNavigateToDonation = { navController.navigate(Routes.Donation.route) },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(Routes.Profile.route){
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSettingsClick = {
                    navController.navigate(Routes.Settings.route)
                }
            )
        }

        composable(Routes.Settings.route) {
            com.example.feederku.views.profile.settings.SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onAccountInfoClick = { navController.navigate(Routes.AccountInformation.route) },
                onEditProfileClick = { navController.navigate(Routes.EditProfile.route) },
                onNotificationsClick = { navController.navigate(Routes.Notifications.route) },
                onPrivacySecurityClick = { navController.navigate(Routes.PrivacySecurity.route) },
                onChangePasswordClick = { navController.navigate(Routes.ChangePassword.route) },
                onLanguageClick = { navController.navigate(Routes.Language.route) },
                onHelpSupportClick = { navController.navigate(Routes.HelpSupport.route) },
                onAboutClick = { navController.navigate(Routes.AboutFeederKu.route) },
                onLogoutClick = { navController.logoutAndNavigateToLogin() }
            )
        }

        composable(Routes.AccountInformation.route) {
            com.example.feederku.views.profile.settings.AccountInfoScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.EditProfile.route) {
            com.example.feederku.views.profile.settings.EditProfileScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack() }
            )
        }

        composable(Routes.Notifications.route) {
            com.example.feederku.views.profile.settings.NotificationsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.PrivacySecurity.route) {
            com.example.feederku.views.profile.settings.PrivacySecurityScreen(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = { navController.logoutAndNavigateToLogin() }
            )
        }

        composable(Routes.ChangePassword.route) {
            com.example.feederku.views.profile.settings.ChangePasswordScreen(
                onBackClick = { navController.popBackStack() },
                onPasswordUpdated = { navController.popBackStack() }
            )
        }

        composable(Routes.Language.route) {
            com.example.feederku.views.profile.settings.LanguageScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.HelpSupport.route) {
            com.example.feederku.views.profile.settings.HelpSupportScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.AboutFeederKu.route) {
            com.example.feederku.views.profile.settings.AboutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Live Stream Screen
        composable(Routes.LiveStreamFlow.route) {
            LiveStreamRoute(
                onBackToHome = {
                    navController.popBackStack(Routes.Dashboard.route, inclusive = false)
                }
            )
        }

        // --- Donation Flow ---
        composable(Routes.Donation.route) { backStackEntry ->
            val donationViewModel = rememberDonationViewModel(navController, backStackEntry)
            DonationRoute(
                onNavigateToDetails = { id -> navController.navigate(Routes.DonationDetails.createRoute(id)) },
                onNavigateToRegister = { navController.navigate(Routes.DonationRegistration.route) },
                onNavigateToHome = { navController.navigate(Routes.Dashboard.route) },
                onNavigateToMap = { navController.navigate(Routes.Map.route) },
                onNavigateToChat = { navController.navigate(Routes.Chat.route) },
                viewModel = donationViewModel
            )
        }

        composable(Routes.DonationDetails.route) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getString("campaignId") ?: ""
            val donationViewModel = rememberDonationViewModel(navController, backStackEntry)
            DonationDetailsScreen(
                campaignId = campaignId,
                onBackClick = { navController.popBackStack() },
                onDonateClick = { id -> navController.navigate(Routes.DonationPaymentMethod.createRoute(id)) },
                onHomeClick = { navController.navigate(Routes.Dashboard.route) },
                onMapClick = { navController.navigate(Routes.Map.route) },
                onChatClick = { navController.navigate(Routes.Chat.route) },
                viewModel = donationViewModel
            )
        }

        composable(Routes.DonationPaymentMethod.route) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getString("campaignId") ?: ""
            val donationViewModel = rememberDonationViewModel(navController, backStackEntry)
            DonationPaymentMethodScreen(
                campaignId = campaignId,
                onBackClick = { navController.popBackStack() },
                onContinueClick = { campaignIdParam ->
                    navController.navigate(Routes.DonationPayment.createRoute(campaignIdParam))
                },
                viewModel = donationViewModel
            )
        }

        composable(Routes.DonationCardDetails.route) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getString("campaignId") ?: ""
            val donationViewModel = rememberDonationViewModel(navController, backStackEntry)
            com.example.feederku.views.donation.DonationCardDetailsScreen(
                campaignId = campaignId,
                onBackClick = { navController.popBackStack() },
                onContinueClick = {
                    navController.navigate(Routes.DonationPayment.createRoute(campaignId))
                },
                viewModel = donationViewModel
            )
        }

        composable(Routes.DonationPayment.route) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getString("campaignId") ?: ""
            val donationViewModel = rememberDonationViewModel(navController, backStackEntry)
            DonationPaymentScreen(
                campaignId = campaignId,
                onBackClick = { navController.popBackStack() },
                onConfirmClick = { navController.navigate(Routes.DonationProcessing.route) },
                viewModel = donationViewModel
            )
        }

        composable(Routes.DonationProcessing.route) {
            DonationProcessingScreen(
                onProcessingFinished = { navController.navigate(Routes.DonationSuccess.createRoute("donation")) }
            )
        }

        composable(Routes.DonationRegistration.route) { backStackEntry ->
            val donationViewModel = rememberDonationViewModel(navController, backStackEntry)
            DonationRegistrationScreen(
                viewModel = donationViewModel,
                onBackClick = { navController.popBackStack() },
                onSuccess = { navController.navigate(Routes.DonationSuccess.createRoute("campaign")) }
            )
        }

        composable(Routes.DonationSuccess.route) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "donation"
            DonationSuccessScreen(
                type = type,
                onBackToCampaigns = {
                    navController.navigate(Routes.Donation.route) {
                        popUpTo(Routes.Donation.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

private fun NavHostController.goToDashboard() {
    navigate(Routes.Dashboard.route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

private fun NavHostController.goBack() {
    if (previousBackStackEntry != null) popBackStack()
}

private fun NavHostController.logoutAndNavigateToLogin() {
    FirebaseAuth.getInstance().signOut()
    navigate(Routes.Welcome.route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

@Composable
private fun rememberDonationViewModel(navController: NavHostController, backStackEntry: androidx.navigation.NavBackStackEntry): DonationViewModel {
    val viewModelStoreOwner = remember(backStackEntry) {
        try {
            navController.getBackStackEntry(Routes.Donation.route)
        } catch (_: Exception) {
            backStackEntry
        }
    }
    return viewModel(viewModelStoreOwner, factory = DonationViewModel.Factory)
}
