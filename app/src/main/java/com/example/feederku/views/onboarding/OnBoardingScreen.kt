package com.example.feederku.views.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.FeederkuTheme
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.onboarding.pages.OnBoardingScreen1
import com.example.feederku.views.onboarding.pages.OnBoardingScreen2
import com.example.feederku.views.onboarding.pages.OnBoardingScreen3

@Composable
fun OnBoardingRoute(
    onFinish: () -> Unit, //navigasi ke SignUp
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OnBoardingScreen(
        uiState = uiState,
        onSkip = onFinish,
        onNext = {
            if(uiState.isLastPage) onFinish() // "Let's Go"
            else viewModel.onNextClicked() // ganti "Get Started" / "Next"
        },
        modifier = modifier
    )
}
@Composable
fun OnBoardingScreen(
    uiState: OnBoardingUiState,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(creamBackground)
    ) {
        // Halaman OnBoarding (Screen 1, 2, 3)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = uiState.currentPage,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "PageTransition"
            ) { page ->
                when (page) {
                    1 -> OnBoardingScreen1(onSkip = onSkip)
                    2 -> OnBoardingScreen2()
                    else -> OnBoardingScreen3()
                }
            }
        }

        OnBoardingFooter(
            currentPage = uiState.currentPage,
            isLastPage = uiState.isLastPage,
            onNext = onNext
        )
    }
}

@Composable
private fun OnBoardingFooter(
    currentPage: Int,
    isLastPage: Boolean,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "$currentPage out of ${OnBoardingUiState.TOTAL_PAGES}",
            fontSize = 12.sp,
            color = textBrown,
            fontFamily = DMSans
        )
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonGreen,
                contentColor = Color.White
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = when (currentPage) {
                        1 -> "Get Started"
                        2 -> "Next"
                        else -> "Let's Go"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DMSans
                )
                if (!isLastPage) {
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Preview(name = "Page 1", showBackground = true, showSystemUi = true)
@Composable
private fun OnBoardingPage1Preview() {
    FeederkuTheme { OnBoardingScreen(OnBoardingUiState(currentPage = 1), onSkip = {}, onNext = {}) }
}

@Preview(name = "Page 2", showBackground = true, showSystemUi = true)
@Composable
private fun OnBoardingPage2Preview() {
    FeederkuTheme { OnBoardingScreen(OnBoardingUiState(currentPage = 2), onSkip = {}, onNext = {}) }
}

@Preview(name = "Page 3", showBackground = true, showSystemUi = true)
@Composable
private fun OnBoardingPage3Preview() {
    FeederkuTheme { OnBoardingScreen(OnBoardingUiState(currentPage = 3), onSkip = {}, onNext = {}) }
}