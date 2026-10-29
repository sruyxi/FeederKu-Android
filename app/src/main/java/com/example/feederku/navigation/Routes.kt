package com.example.feederku.navigation

//buat view model dan kumpulin uistate
sealed class Routes(val route: String){
    data object OnBoarding : Routes("onboarding")
    data object Welcome : Routes("welcome")
    data object SignUp : Routes("signup")
    data object Login : Routes("login")
    data object ForgotPassword : Routes("forgot_password")
    data object VerifyEmail : Routes("verify_email")
    data object Dashboard : Routes("dashboard")
    data object Profile : Routes("profile")
}