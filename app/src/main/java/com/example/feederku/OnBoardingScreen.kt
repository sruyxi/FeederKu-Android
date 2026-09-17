package com.example.feederku

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.FeederkuTheme

data class FiturFeeder(val id: Int, val nama: String, val resId: Int, val desc: String)
@Composable
fun OnBoardingScreen (modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Image(
            painter = painterResource(id = R.drawable.foto_kucing),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                contentScale = ContentScale.Crop
        )
        }
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(Color(0xFFFAF6EF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Feederku",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF5A4033),
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "Help Stray Cats Around You!",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF5A4033),
                modifier = Modifier.padding(top = 15.dp)
            )


            Spacer(modifier = Modifier.height(40.dp))

            // FITUR FEEDERKU
            val fiturFeeder = listOf(
                FiturFeeder(id = 1, nama = "Active Feeding", resId = R.drawable.vector_paw, desc = "Report sighted strays and log when you feed them."),
                FiturFeeder(id = 2, nama = "Emphatetic Donations", resId = R.drawable.vector_love, desc = "Support local stray rescue & medical campaigns."),
                FiturFeeder(id = 3, nama = "Community Exploration", resId = R.drawable.vector_location, desc = "Connect with certified feeders in your city area."),
            )

            fiturFeeder.forEach { fitur ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0EAD8)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE6DDC8),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(10.dp),
                                contentAlignment = Alignment.Center
                        ){
                        Image(
                            painter = painterResource(id = fitur.resId),
                            contentDescription = null,
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                        Column(
                            modifier = Modifier.padding(start = 20.dp)
                        ){
                            Text(
                                text = fitur.nama,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF5A4033)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = fitur.desc,
                                fontFamily = FontFamily.Default,
                                color = Color(0xFF5A4033),
                                style = TextStyle(fontSize = 10.sp)
                            )
                        }

                    }
                }
            }
        }

        //Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFAF6EF))
                .padding(top = 0.dp, bottom = 30.dp),
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
                text = "Get Started",
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