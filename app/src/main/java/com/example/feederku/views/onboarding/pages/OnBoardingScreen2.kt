package com.example.feederku.views.onboarding.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.feederku.R
import com.example.feederku.ui.theme.FeederkuTheme

@Composable
fun OnBoardingScreen2 (
    modifier: Modifier = Modifier
) {
    OnBoardingImagePage(
        imageRes = R.drawable.ellipse_1,
        title = "Feed Strays Near You",
        description = "Locate feeding points and coordinates with fellow " +
                "cat lovers" + "to make sure no stray cats go hungry tonight",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
private fun OnBoarding2Preview() {
    FeederkuTheme {
        OnBoardingScreen2()
    }
}