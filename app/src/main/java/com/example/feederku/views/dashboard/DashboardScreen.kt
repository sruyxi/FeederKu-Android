package com.example.feederku.views.dashboard

import com.example.feederku.views.dashboard.components.DashboardFooter
import com.example.feederku.views.dashboard.components.DashboardHeader
import com.example.feederku.views.dashboard.components.FeedPostCard
import com.example.feederku.views.dashboard.components.ImpactCard
import com.example.feederku.views.dashboard.components.LiveStreamCard
import com.example.feederku.views.dashboard.components.SearchBarSection
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.views.dashboard.components.SectionHeader
import com.example.feederku.views.dashboard.model.DashboardTab
import com.example.feederku.views.dashboard.model.DashboardUiState
import com.example.feederku.views.dashboard.model.FeedPost
import com.example.feederku.views.dashboard.model.LiveStream

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
                onAddClick = onAddClick,
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
            else ->
                Box(contentModifier, contentAlignment = Alignment.Center){
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
    //Logika pencarian memfilter feed dynamic
    val filteredPosts = remember(uiState.searchQuery, uiState.feedPosts){
        if (uiState.searchQuery.isBlank()) {
            uiState.feedPosts
        } else {
            uiState.feedPosts.filter { post ->
                post.author.contains(uiState.searchQuery, ignoreCase = true) ||
                        post.content.contains(uiState.searchQuery, ignoreCase = true)
            }
        }
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 88.dp), // space untuk tombol Go Live
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        // Search Bar
        item {
            SearchBarSection(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
        }
        // Live Stream Section
        item {
            Column( modifier = Modifier.fillMaxWidth()) {
                SectionHeader(
                    title = "Live Stream Feed",
                    onSeeAllClick = onSeeAllClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    contentPadding = PaddingValues (horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    items(uiState.liveStream, key = { it.id }) { stream ->
                        LiveStreamCard(
                            stream = stream,
                            onClick = { onLiveStreamClick(stream) }
                        )
                    }
                }
            }
        }
        // Impact Stats Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(title = "Your Impact")
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    items(uiState.impactStats) { stat ->
                        ImpactCard(stat = stat)
                    }
                }
            }
        }
        // Community Feed Posts
        if (filteredPosts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = if (uiState.searchQuery.isBlank())
                            "Belum ada postingan."
                        else
                            "Tidak ada postingan yang cocok dengan \"${uiState.searchQuery}\"",
                        color = textBrown.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontFamily = DMSans
                    )
                }
            }
        } else {
            items( filteredPosts, key = { it.id}) { post ->
                FeedPostCard(
                    post = post,
                    onMenuClick = { onPostMenuClick(post) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
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