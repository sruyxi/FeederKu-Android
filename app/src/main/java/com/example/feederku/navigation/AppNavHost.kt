package com.example.feederku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feederku.views.onboarding.OnBoardingRoute
import com.example.feederku.views.signup.SignUp

//pengatur layar
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.OnBoarding.route,
        modifier = modifier
    ) {
        composable(Routes.OnBoarding.route) {
            OnBoardingRoute(
                onFinish = {
                    navController.navigate(Routes.SignUp.route) {
                        popUpTo(Routes.OnBoarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SignUp.route) {
            SignUp()
        }
    }
}