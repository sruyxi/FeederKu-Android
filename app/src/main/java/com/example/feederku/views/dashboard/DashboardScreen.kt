package com.example.feederku.views.dashboard

import com.example.feederku.views.dashboard.components.DashboardFooter
import com.example.feederku.views.dashboard.components.DashboardHeader
/*import com.example.feederku.views.dashboard.components.FeedPostCard
import com.example.feederku.views.dashboard.components.ImpactStatsSection
import com.example.feederku.views.dashboard.components.LiveStreamsSection*/
import com.example.feederku.views.dashboard.components.SearchBarSection
import com.example.feederku.views.dashboard.components.SectionHeader
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feederku.ui.theme.FeederkuTheme
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.dashboard.data.FakeDashboardRepository
import com.example.feederku.views.dashboard.model.DashboardTab
import com.example.feederku.views.dashboard.model.DashboardUiState
import com.example.feederku.views.dashboard.model.FeedPost
import com.example.feederku.views.dashboard.model.LiveStream
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.feederku.ui.theme.DMSans

@Composable
fun DashboardRoute(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    onGoLiveClick: () -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onLiveStreamClick: (LiveStream) -> Unit = {},
    onPostMenuClick: (FeedPost) -> Unit = {},
    onProfileClick: () -> Unit = {},
    viewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory)
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onTabSelected = viewModel::onTabSelected,
        onRetry = viewModel::loadDashboard,
        onAddClick = onAddClick,
        onGoLiveClick = onGoLiveClick,
        onSeeAllClick = onSeeAllClick,
        onLiveStreamClick = onLiveStreamClick,
        onPostMenuClick = onPostMenuClick,
        onProfileClick = onProfileClick,
        modifier = modifier
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onSearchQueryChange: (String) -> Unit,
    onTabSelected: (DashboardTab) -> Unit,
    onRetry: () -> Unit,
    onAddClick: () -> Unit,
    onGoLiveClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    onLiveStreamClick: (LiveStream) -> Unit,
    onPostMenuClick: (FeedPost) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = creamBackground,
        topBar = {
            DashboardHeader(
                userName = uiState.userName,
                userTitle = uiState.userTitle,
                onAddClick = {},
                onProfileClick = onProfileClick
            )
        },
        bottomBar = {
            DashboardFooter(
                selectedTab = uiState.selectedTab, onTabClick = onTabSelected
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onGoLiveClick,
                containerColor = buttonGreen,
                contentColor = Color.White,
                shape = RoundedCornerShape(24.dp),
                icon = {
                    Icon(Icons.Default.Videocam,
                        contentDescription = "Go Live")
                    },
                text = {
                    Text(
                        "Go Live",
                        fontWeight = FontWeight.Bold,
                        fontFamily = DMSans
                    )
                }
            )
        }
    ) { innerPadding ->
        val contentModifier = Modifier.fillMaxSize().padding(innerPadding)
        when {
            uiState.isLoading -> Box (contentModifier, contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = buttonGreen)
            }
            uiState.errorMessage != null -> Column(
                modifier = contentModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(uiState.errorMessage, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(12.dp))
                Button(onClick = onRetry) { Text("Coba lagi") }
            }
            uiState.selectedTab == DashboardTab.Home -> HomeContent(
                uiState = uiState,
                onSearchQueryChange = onSearchQueryChange,
                onSeeAllClick = onSeeAllClick,
                onLiveStreamClick = onLiveStreamClick,
                onPostMenuClick = onPostMenuClick,
                modifier = contentModifier
            )
            else -> Box(contentModifier, contentAlignment = Alignment.Center){
                Text("${uiState.selectedTab.label} segera hadir", color = textBrown)
            }
        }
    }
}

@Composable
private fun HomeContent(
    uiState: DashboardUiState,
    onSearchQueryChange: (String) -> Unit,
    onSeeAllClick: () -> Unit,
    onLiveStreamClick: (LiveStream) -> Unit,
    onPostMenuClick: (FeedPost) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 88.dp), // space untuk tombol Go Live
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item {
            SearchBarSection(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
        }
        /*item {
            LiveStreamsSection(
                streams = uiState.liveStream,
                onSeeAllClick = onSeeAllClick,
                onStreamClick = onLiveStreamClick
            )
        }*/
        /*item {
            ImpactStatsSection(stats = uiState.impactStats)
        }
        item {
            SectionHeader(
                title = "Recent Community Feed",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }*/
        /*items(uiState.feedPosts, key = { it.id } ) { post ->
            FeedPostCard(
                post = post,
                onMenuClick = { onPostMenuClick(post) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }*/
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardScreenPreview(){
    val data = FakeDashboardRepository.sampleData
    FeederkuTheme {
        DashboardScreen(
            uiState = DashboardUiState(
                userName = data.userName,
                userTitle = data.userTitle,
                liveStream = data.liveStream,
                impactStats = data.impactStats,
                feedPosts = data.feedPosts
            ),
            onSearchQueryChange = {},
            onTabSelected = {},
            onRetry = {},
            onAddClick = {},
            onGoLiveClick = {},
            onSeeAllClick = {},
            onLiveStreamClick = {},
            onPostMenuClick = {},
            onProfileClick = {}
        )
    }
}