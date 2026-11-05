package com.example.feederku.views.community.data

import com.example.feederku.views.community.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseChatRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ChatRepository {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    // Listens to the newest [limit] messages and emits them oldest-first
    override fun observeMessages(limit: Int): Flow<List<Message>> = callbackFlow {
        val listener = db.collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error) // the ViewModel catches this and shows an error
                    return@addSnapshotListener
                }
                val list = snapshot?.documents?.map { doc ->
                    Message(
                        id = doc.id,
                        senderId = doc.getString("senderId").orEmpty(),
                        senderName = doc.getString("senderName").orEmpty(),
                        text = doc.getString("text").orEmpty(),
                        timestamp = doc.getTimestamp(
                            "timestamp",
                            DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                        )?.toDate()?.time ?: 0L
                    )
                }.orEmpty().reversed()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun sendMessage(text: String) {
        val user = auth.currentUser ?: throw IllegalStateException("Not logged in")
        val data = hashMapOf(
            "senderId" to user.uid,
            "senderName" to (user.displayName ?: user.email?.substringBefore("@") ?: "Anonymous"),
            "text" to text,
            "timestamp" to FieldValue.serverTimestamp()
        )
        db.collection("messages").add(data).await()
    }
}