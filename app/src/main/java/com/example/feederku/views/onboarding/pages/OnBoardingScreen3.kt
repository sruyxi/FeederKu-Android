package com.example.feederku.views.onboarding.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.feederku.R
import com.example.feederku.ui.theme.FeederkuTheme

@Composable
fun OnBoardingScreen3 (
    modifier: Modifier = Modifier
) {
    OnBoardingImagePage(
        imageRes = R.drawable.ellipse_2,
        title = "Join the community",
        description = "Connect with certified shelters, join " +
                "localized chat groups, " + "and share verified updates about rescues",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun OnBoarding3Preview() {
    FeederkuTheme {
        OnBoardingScreen3()
    }
}