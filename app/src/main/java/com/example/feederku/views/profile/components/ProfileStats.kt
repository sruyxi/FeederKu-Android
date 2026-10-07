package com.example.feederku.views.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.cardProfile
import com.example.feederku.ui.theme.greenDark
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.profile.model.ProfileUser

@Composable
fun ProfileStats(
    user: ProfileUser,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardProfile
            )
        ){
            Column(
                modifier = Modifier.padding(12.dp)
            ){
                Text(
                    text = "${user.catsFed}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textBrown,
                    fontFamily = DMSans
                )
                Text(
                    text = "Cats Fed",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DMSans,
                    color = textBrown
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardProfile
            )
        ){
            Column(
                modifier = Modifier.padding(12.dp)
            ){
                Text(
                    text = "${user.locations}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textBrown,
                    fontFamily = DMSans
                )
                Text(
                    text = "Locations",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DMSans,
                    color = textBrown
                )
            }
        }
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardProfile
            )
        ){
            Column(
                modifier = Modifier.padding(12.dp)
            ){
                Text(
                    text = "${user.catsAdopted}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textBrown,
                    fontFamily = DMSans
                )
                Text(
                    text = "Cats Adopted",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DMSans,
                    color = greenDark
                )
            }
        }
    }
}