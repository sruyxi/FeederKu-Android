package com.example.feederku.views.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.views.profile.components.DonationItem
import com.example.feederku.views.profile.components.HistoryItems
import com.example.feederku.views.profile.components.ProfileHeader
import com.example.feederku.views.profile.components.ProfileInfo
import com.example.feederku.views.profile.components.ProfileStats
import com.example.feederku.views.profile.components.ProfileTabs

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(creamBackground)
    ){
        ProfileHeader(
            onBackClick = onBackClick,
            onSettingsClick = onSettingsClick
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            item {
                ProfileInfo(uiState.user)
            }
            item {
                ProfileStats(uiState.user)
            }
            item{
                ProfileTabs(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = {
                        viewModel.selectTab(it)
                    }
                )
            }

            if(uiState.selectedTab == 0){
                items(uiState.feedingHistory) { item ->
                    HistoryItems(item)
                }
            } else {
                items(uiState.donationHistory) { item ->
                    DonationItem(item)
                }
            }
        }
    }
}