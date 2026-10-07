package com.example.feederku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feederku.views.auth.pages.LoginRoute
import com.example.feederku.views.auth.pages.SignUpRoute
import com.example.feederku.views.auth.pages.WelcomeScreen
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
                }
            )
        }
        
        composable(Routes.Dashboard.route){
            DashboardRoute(
                onProfileClick = {
                    navController.navigate(Routes.Profile.route)
                }
            )
        }

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