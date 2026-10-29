package com.example.feederku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feederku.views.auth.AuthViewModel
import com.example.feederku.views.auth.ForgotPasswordScreen
import com.example.feederku.views.auth.LoginRoute
import com.example.feederku.views.auth.SignUpRoute
import com.example.feederku.views.auth.VerifyEmailScreen
import com.example.feederku.views.auth.WelcomeScreen
import com.example.feederku.views.dashboard.DashboardRoute
import com.example.feederku.views.onboarding.OnBoardingRoute
import com.example.feederku.views.profile.ProfileScreen

//pengatur layar
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.OnBoarding.route,
        modifier = modifier
    ) {
        // OnBoarding Screen
        composable(Routes.OnBoarding.route) {
            OnBoardingRoute(
                onFinish = {
                    navController.navigate(Routes.Welcome.route) {
                        popUpTo(Routes.OnBoarding.route) { inclusive = true }
                    }
                }
            )
        }

        // Welcome Screen
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

        // Sign Up Screen
        composable(Routes.SignUp.route) {
            SignUpRoute(
                onNavigateToLogin= {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Welcome.route) { inclusive = true }
                    }
                },
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

        // Login Screen
        composable(Routes.Login.route) {
            LoginRoute(
                onLoginSuccess = {
                    navController.navigate(Routes.Dashboard.route) {
                        popUpTo(Routes.Welcome.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
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

        // Forgot Password Screen
        composable(Routes.ForgotPassword.route) {
            val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

            ForgotPasswordScreen(
                uiState = uiState,
                onEmailChange = authViewModel::onEmailChange,
                onSendResetLinkClick = {
                    navController.navigate(Routes.VerifyEmail.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Verify Email Screen
        composable(Routes.VerifyEmail.route){
            val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

            VerifyEmailScreen(
                uiState = uiState,
                onBackToLoginClick = {
                    navController.navigate(Routes.Login.route){
                        popUpTo(Routes.Welcome.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Dashboard Screen
        composable(Routes.Dashboard.route){
            DashboardRoute(
                onProfileClick = {
                    navController.navigate(Routes.Profile.route)
                }
            )
        }

        // Profile Screen
        composable(Routes.Profile.route){
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSettingsClick = {

                }
            )
        }
    }
}

/* Hanya ke Dashboard dan hapus semua halaman sebelumnya, sehingga kalo klik tombol back
ngga balik ke login atau signup lagi
*/
private fun NavHostController.goToDashboard() {
    navigate(Routes.Dashboard.route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

//Kembali jika ada layar sebelumnya
private fun NavHostController.goBack() {
    if (previousBackStackEntry != null) popBackStack()
}