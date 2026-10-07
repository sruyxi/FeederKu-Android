package com.example.feederku.views.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val tabs = listOf(
        "Feeding History",
        "Donation History"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        PrimaryTabRow(
            selectedTabIndex = selectedTab
        ){
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        onTabSelected(index)
                    },
                    text = {
                        Text(
                            text = title
                        )
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
    }
}