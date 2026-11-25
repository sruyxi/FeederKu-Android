package com.example.feederku.views.community

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feederku.ui.theme.*
import com.example.feederku.views.community.model.ChatMessage
import com.example.feederku.views.community.network.ConnectionStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealtimeChatScreen(
    viewModel: ChatViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.wsMessages.collectAsStateWithLifecycle()
    val connectionState by viewModel.wsConnectionState.collectAsStateWithLifecycle()
    val inputText by viewModel.wsInputText.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Live WS Chat", fontWeight = FontWeight.Bold, color = Color.White)
                        ConnectionStatusBadge(connectionState)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = headerFooter)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ColorFogCream,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = viewModel::onWsInputChange,
                        placeholder = { Text("Type a message...", color = textBrown.copy(alpha = 0.5f)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = buttonGreen,
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )
                    IconButton(
                        onClick = { viewModel.sendWsMessage() },
                        modifier = Modifier
                            .size(48.dp)
                            .background(buttonGreen, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        },
        containerColor = ColorFogCream
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { message ->
                ChatBubbleItem(message)
            }
        }
    }
}

@Composable
fun ConnectionStatusBadge(status: ConnectionStatus) {
    val (color, text) = when (status) {
        ConnectionStatus.Connected -> Color(0xFF4CAF50) to "Connected"
        ConnectionStatus.Connecting -> Color(0xFFFFC107) to "Connecting..."
        ConnectionStatus.Disconnected -> Color.Gray to "Disconnected"
        ConnectionStatus.Error -> Color(0xFFE57373) to "Error"
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.2f),
        border = BorderStroke(1.dp, color)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun ChatBubbleItem(message: ChatMessage) {
    val alignment = if (message.isFromMe) Alignment.End else Alignment.Start
    val bubbleColor = if (message.isFromMe) buttonGreen else Color.White
    val textColor = if (message.isFromMe) Color.White else textBrown

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        if (!message.isFromMe) {
            Text(
                text = message.senderName,
                fontSize = 10.sp,
                color = textBrown.copy(alpha = 0.7f),
                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
            )
        }
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isFromMe) 16.dp else 4.dp,
                bottomEnd = if (message.isFromMe) 4.dp else 16.dp
            ),
            color = bubbleColor,
            shadowElevation = 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    fontSize = 14.sp,
                    color = textColor,
                    fontFamily = DMSans
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                    fontSize = 9.sp,
                    color = textColor.copy(alpha = 0.7f),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
