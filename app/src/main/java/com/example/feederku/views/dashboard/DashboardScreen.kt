package com.example.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// COLOR PALETTE (From Reference)
// ==========================================
val ColorTimber = Color(0xFF3A2E26)   // Heading / Nav Background[cite: 2]
val ColorLinenWarm = Color(0xFFF0EAD8) // Card BG / Highlight[cite: 2]
val ColorJungle = Color(0xFF5CB85C)    // CTA Button Green[cite: 2]
val ColorFogCream = Color(0xFFFAF6EE)  // Page BG[cite: 2]

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DashboardScreen()
            }
        }
    }
}

@Composable
fun DashboardScreen() {
    Scaffold(
        containerColor = ColorFogCream,
        topBar = {
            DashboardHeader()
        },
        bottomBar = {
            DashboardFooter()
        },
        floatingActionButton = {
            // Floating "Go Live" Button from Design[cite: 1]
            ExtendedFloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = ColorJungle,
                contentColor = Color.White,
                shape = RoundedCornerShape(24.dp),
                icon = { Icon(Icons.Default.Videocam, contentDescription = "Go Live") },
                text = { Text("Go Live", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        // Menggunakan Column + verticalScroll agar seluruh isi dashboard bisa di-scroll ke bawah
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp) // Jarak pengetikan dari sisi kiri & kanan layar
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp) // Jarak otomatis antar bagian content
        ) {
            SearchBarSection()
            LiveStreamsSection()
            ImpactStatsSection()
            CommunityFeedSection()
        }
    }
}

// ==========================================
// 1. HEADER SECTION
// ==========================================
@Composable
fun DashboardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorTimber)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Tempat Foto Profil (Placeholder)[cite: 1]
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ColorLinenWarm),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    color = ColorTimber,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Hi, Sarah!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Certified Stray Protector",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }
        }

        // Tombol Tambah (+)[cite: 1]
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .size(40.dp)
                .background(ColorJungle, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Post",
                tint = Color.White
            )
        }
    }
}

// ==========================================
// 2. CONTENT SECTIONS (Urutan sesuai Gambar)
// ==========================================
@Composable
fun SearchBarSection() {
    // Tempat untuk kolom pencarian ("Search stray locations...")[cite: 1]
}

@Composable
fun LiveStreamsSection() {
    // Tempat untuk baris horizontal "Recent Live Streams" dan daftar kartu live[cite: 1]
}

@Composable
fun ImpactStatsSection() {
    // Tempat untuk kartu statistik ("Your Impact This Month": Cats Fed, Locations, dll.)[cite: 1]
}

@Composable
fun CommunityFeedSection() {
    // Tempat untuk daftar kiriman komunitas ("Recent Community Feed") beserta foto kucingnya[cite: 1]
}

// ==========================================
// 3. FOOTER SECTION
// ==========================================
@Composable
fun DashboardFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorTimber)
            .navigationBarsPadding()
            .padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ikon Home (Active State)[cite: 1]
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.15f), CircleShape)
                .size(44.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = Color.White
            )
        }

        // Ikon Lokasi[cite: 1]
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Locations",
                tint = Color.White.copy(alpha = 0.7f)
            )
        }

        // Ikon Pesan[cite: 1]
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.ChatBubbleOutline,
                contentDescription = "Messages",
                tint = Color.White.copy(alpha = 0.7f)
            )
        }

        // Ikon Favorit[cite: 1]
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favorites",
                tint = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

// ==========================================
// 4. PREVIEW (Tampilan tanpa harus run app)
// ==========================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen()
    }
}