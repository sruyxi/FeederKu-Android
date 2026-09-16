package com.example.feederku

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.FeederkuTheme

data class FiturFeeder(val id: Int, val nama: String)
@Composable
fun OnBoardingScreen (modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Header
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Image(
            painter = painterResource(id = R.drawable.foto_kucing),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
                contentScale = ContentScale.Crop
        )
        }
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF0EAD8)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Feederku",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF5A4033)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Help Stray Cats Around You!",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF5A4033)
            )


            Spacer(modifier = Modifier.height(20.dp))

            // FITUR FEEDERKU
            val fiturFeeder = listOf(
                FiturFeeder(id = 1, nama = "Active Feeding"),
                FiturFeeder(id = 2, nama = "Emphatetic Donations"),
                FiturFeeder(id = 3, nama = "Community Exploration"),
            )

            fiturFeeder.forEach { fitur ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE6DDC8)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = fitur.nama,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }

        //Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0EAD8))
                .padding(top = 0.dp, bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "1 out of 3",
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
            Spacer(modifier = Modifier.height(1.dp))
            Button(
                onClick = { },

                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF74B567),
                    contentColor = Color.White
                )
            ){
            Text(
                text = "Get Started →",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    FeederkuTheme {
        OnBoardingScreen()
    }
}