package com.example.feederku.navigation

//buat view model dan kumpulin uistate
sealed class Routes(val route: String){
    data object OnBoarding : Routes("onboarding")
    data object SignUp : Routes("signup")
}