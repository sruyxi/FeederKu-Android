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
import androidx.compose.ui.text.style.TextAlign
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
import java.util.Calendar
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
        onLoadOlderClick = viewModel::onLoadOlderClick,
        onRetryClick = viewModel::retry,
        onErrorShown = viewModel::onErrorShown,
        modifier = modifier
    )
}

@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onLoadOlderClick: () -> Unit,
    onRetryClick: () -> Unit,
    onErrorShown: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    val chatItems = remember(uiState.messages, uiState.canLoadMore, uiState.isLoadingOlder) {
        buildChatItems(uiState.messages, uiState.canLoadMore, uiState.isLoadingOlder)
    }

    // Scroll down only when the NEWEST message changes,
    // so loading older messages doesn't jump the list to the bottom
    LaunchedEffect(uiState.messages.lastOrNull()?.id) {
        if (chatItems.isNotEmpty()) {
            listState.animateScrollToItem(chatItems.lastIndex)
        }
    }

    // Show errors as a snackbar, then clear them
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorShown()
        }
    }

    Column(
        modifier = modifier
            .background(creamBackground)
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = buttonGreen
                )

                uiState.loadFailed && uiState.messages.isEmpty() -> Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Couldn't load messages.", color = textBrown)
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onRetryClick,
                        colors = ButtonDefaults.buttonColors(containerColor = buttonGreen)
                    ) {
                        Text("Try again")
                    }
                }

                uiState.messages.isEmpty() -> Text(
                    text = "No messages yet.\nStart the conversation!",
                    color = textBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp)
                )

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(chatItems, key = { it.key }) { item ->
                        when (item) {
                            is ChatItem.LoadOlder -> LoadOlderItem(
                                loading = item.loading,
                                onClick = onLoadOlderClick
                            )
                            is ChatItem.DateHeader -> DateHeader(item.label)
                            is ChatItem.Bubble -> MessageBubble(
                                message = item.message,
                                isMine = item.message.senderId == uiState.currentUserId
                            )
                        }
                    }
                }
            }
        }

        SnackbarHost(hostState = snackbarHostState)

        MessageInput(
            value = uiState.input,
            maxLength = ChatViewModel.MAX_MESSAGE_LENGTH,
            onValueChange = onInputChange,
            onSendClick = onSendClick
        )
    }
}

// ---------- List items ----------

private sealed interface ChatItem {
    val key: String

    data class LoadOlder(val loading: Boolean) : ChatItem {
        override val key: String get() = "load_older"
    }

    data class DateHeader(val label: String, val dayKey: String) : ChatItem {
        override val key: String get() = "date_$dayKey"
    }

    data class Bubble(val message: Message) : ChatItem {
        override val key: String get() = message.id
    }
}

private fun buildChatItems(
    messages: List<Message>,
    canLoadMore: Boolean,
    loadingOlder: Boolean
): List<ChatItem> {
    val items = mutableListOf<ChatItem>()
    if (canLoadMore) items += ChatItem.LoadOlder(loadingOlder)

    var lastDay = ""
    for (message in messages) {
        val day = dayKey(message.timestamp)
        if (day != lastDay) {
            items += ChatItem.DateHeader(dayLabel(message.timestamp), day)
            lastDay = day
        }
        items += ChatItem.Bubble(message)
    }
    return items
}

// ---------- UI pieces ----------

@Composable
private fun LoadOlderItem(loading: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = buttonGreen,
                strokeWidth = 2.dp
            )
        } else {
            TextButton(onClick = onClick) {
                Text("Load older messages", color = buttonGreen)
            }
        }
    }
}

@Composable
private fun DateHeader(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = textBrown.copy(alpha = 0.12f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = textBrown,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
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
    maxLength: Int,
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
                placeholder = { Text("Type a message...") },
                shape = RoundedCornerShape(24.dp),
                maxLines = 4,
                // Character counter appears when getting close to the limit
                supportingText = if (value.length >= maxLength - 100) {
                    { Text("${value.length}/$maxLength") }
                } else null
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onSendClick, enabled = value.isNotBlank()) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (value.isNotBlank()) buttonGreen else Color.Gray
                )
            }
        }
    }
}

// ---------- Date / time helpers ----------

private fun formatTime(timestamp: Long): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))

private fun dayKey(timestamp: Long): String =
    SimpleDateFormat("yyyyMMdd", Locale.US).format(Date(timestamp))

private fun dayLabel(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    return when (dayKey(timestamp)) {
        dayKey(now) -> "Today"
        dayKey(yesterday.timeInMillis) -> "Yesterday"
        else -> SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH).format(Date(timestamp))
    }
}