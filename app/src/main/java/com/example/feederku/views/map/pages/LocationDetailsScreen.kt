package com.example.feederku.views.map.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.feederku.ui.theme.*
import com.example.feederku.views.map.model.Sighting
import com.example.feederku.views.map.model.SightingCategory
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    locationId: String,
    sighting: Sighting,
    onBackClick: () -> Unit
) {
    val targetLat = if (sighting.location.latitude != 0.0) sighting.location.latitude else -6.2215
    val targetLng = if (sighting.location.longitude != 0.0) sighting.location.longitude else 106.6135
    val displayAddress = sighting.address.ifBlank { sighting.title }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = sighting.title.ifBlank { "Location Details" },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ID: $locationId • ${sighting.distance}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ColorFogCream)
            )
        },
        bottomBar = {
            CommentInputField()
        },
        containerColor = ColorFogCream
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // MapLibre Preview
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            onCreate(null)
                            getMapAsync { map ->
                                map.setStyle(Style.Builder().fromUri("https://tiles.openfreemap.org/styles/bright")) { _ ->
                                    val point = LatLng(targetLat, targetLng)
                                    map.cameraPosition = CameraPosition.Builder()
                                        .target(point)
                                        .zoom(16.0)
                                        .build()
                                    map.addMarker(
                                        MarkerOptions()
                                            .position(point)
                                            .title(sighting.title)
                                    )
                                }
                                map.uiSettings.isZoomGesturesEnabled = false
                                map.uiSettings.isScrollGesturesEnabled = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(Modifier.height(8.dp))

                // Address Bar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.4f)
                ) {
                    Text(
                        text = displayAddress,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            item {
                Text("Current Status", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textBrown)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatusCard(
                        title = "Status Alert",
                        value = sighting.status.ifBlank { sighting.category.label },
                        subtitle = sighting.description.ifBlank { "Sighting update" },
                        valueColor = if (sighting.category == SightingCategory.URGENT_HELP) liveRed else greenDark,
                        modifier = Modifier.weight(1f)
                    )
                    StatusCard(
                        title = "Category",
                        value = sighting.category.label,
                        subtitle = sighting.distance.ifBlank { "Nearby spot" },
                        valueColor = greenDark,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text("Recent Reports (${mockReports.size})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textBrown)
            }

            items(mockReports) { report ->
                ReportItem(report)
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun StatusCard(
    title: String,
    value: String,
    subtitle: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        color = ColorLinenWarm,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Center) {
            Text(title, fontSize = 11.sp, color = Color.Gray)
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = valueColor)
            Text(subtitle, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

data class Report(
    val author: String,
    val role: String,
    val time: String,
    val content: String,
    val hasImage: Boolean = false
)

val mockReports = listOf(
    Report(
        "@joceypek",
        "Gold Feeder",
        "30 mins ago",
        "Cats haven't been fed since yesterday morning. They look very hungry and need plenty of fresh water."
    ),
    Report(
        "@santet",
        "Active Member",
        "2 hours ago",
        "Spotted around 10 cats here. They look mostly healthy but definitely looking for kibbles near the bush entrance.",
        hasImage = true
    )
)

@Composable
fun ReportItem(report: Report) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = ColorLinenWarm,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(32.dp).clip(CircleShape).background(Color.Gray))
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(report.author, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(report.role, fontSize = 10.sp, color = Color.Gray)
                }
                Spacer(Modifier.weight(1f))
                Text(report.time, fontSize = 11.sp, color = Color.Gray)
            }
            Spacer(Modifier.height(12.dp))
            Text(report.content, fontSize = 13.sp, color = textBrown, lineHeight = 18.sp)
            
            if (report.hasImage) {
                Spacer(Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                ) {
                    Text("Image Placeholder", modifier = Modifier.align(Alignment.Center), color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CommentInputField() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Transparent
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(ColorLinenWarm, RoundedCornerShape(24.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            IconButton(onClick = { }) {
                Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = Color.Gray)
            }
            Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text("Type your message...", color = Color.Gray, fontSize = 14.sp)
            }
            IconButton(
                onClick = { },
                modifier = Modifier.background(buttonGreen, CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
    }
}
