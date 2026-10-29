package com.example.feederku.views.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.community.model.ChatUiState
import com.example.feederku.views.community.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatRoute(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel(factory = ChatViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ChatScreen(
        uiState = uiState,
        onInputChange = viewModel::onInputChange,
        onSendClick = viewModel::onSendClick,
        modifier = modifier
    )
}

@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Scroll to the newest message whenever the count changes
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.lastIndex)
        }
    }

    Column(
        modifier = modifier
            .background(creamBackground)
            .imePadding()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.messages, key = { it.id }) { message ->
                MessageBubble(
                    message = message,
                    isMine = message.senderId == uiState.currentUserId
                )
            }
        }
        MessageInput(
            value = uiState.input,
            onValueChange = onInputChange,
            onSendClick = onSendClick
        )
    }
}

@Composable
private fun MessageBubble(message: Message, isMine: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        if (!isMine) {
            Text(
                text = message.senderName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textBrown,
                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
            )
        }
        Surface(
            color = if (isMine) buttonGreen else Color.White,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    text = message.text,
                    color = if (isMine) Color.White else textBrown
                )
                Text(
                    text = formatTime(message.timestamp),
                    fontSize = 10.sp,
                    color = if (isMine) Color.White.copy(alpha = 0.7f) else Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun MessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(color = Color.White, shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Tulis pesan...") },
                shape = RoundedCornerShape(24.dp),
                maxLines = 4
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onSendClick, enabled = value.isNotBlank()) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Kirim",
                    tint = if (value.isNotBlank()) buttonGreen else Color.Gray
                )
            }
        }
    }
}

private fun formatTime(timestamp: Long): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))