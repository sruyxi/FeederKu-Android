package com.example.feederku.views.donation.data

import com.example.feederku.views.donation.model.DonationCampaign
import com.example.feederku.views.donation.model.Donor
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseDonationRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : DonationRepository {

    private val sampleCampaigns = listOf(
        DonationCampaign(
            id = "camp_1",
            title = "Emergency Medical Care for Rescued Stray Cats",
            organizationName = "Jakarta Animal Aid",
            description = "Help us fund emergency surgeries and medications for rescued stray cats suffering from severe injuries.",
            targetAmount = 5000000,
            collectedAmount = 3450000,
            category = "Medical",
            isVerified = true,
            daysRemaining = 12,
            donorCount = 48
        ),
        DonationCampaign(
            id = "camp_2",
            title = "Monthly Food Supply for 100 Sheltered Cats",
            organizationName = "Paws & Claws Shelter",
            description = "Providing nutritious dry and wet food for over 100 cats currently sheltered at our rescue center.",
            targetAmount = 10000000,
            collectedAmount = 7800000,
            category = "Feeding",
            isVerified = true,
            daysRemaining = 5,
            donorCount = 92
        ),
        DonationCampaign(
            id = "camp_3",
            title = "Shelter Roof Repair & Warm Bedding Project",
            organizationName = "Bandung Cat Rescue",
            description = "Our rescue shelter needs urgent roof repairs before rainy season and comfortable bedding for kittens.",
            targetAmount = 15000000,
            collectedAmount = 11200000,
            category = "Shelter",
            isVerified = true,
            daysRemaining = 8,
            donorCount = 74
        ),
        DonationCampaign(
            id = "camp_4",
            title = "Rescue Van Fuel & Operation Fund",
            organizationName = "Stray Defenders ID",
            description = "Help fuel our ambulance van dedicated to picking up injured or distressed stray animals across Jabodetabek.",
            targetAmount = 3000000,
            collectedAmount = 2100000,
            category = "Rescue",
            isVerified = true,
            daysRemaining = 20,
            donorCount = 35
        )
    )

    override fun getCampaigns(): Flow<List<DonationCampaign>> = callbackFlow {
        val subscription = db.collection("campaigns")
            .addSnapshotListener { snapshot, error ->
                val campaigns = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(DonationCampaign::class.java)?.copy(id = doc.id)
                } ?: emptyList()

                if (error != null || campaigns.isEmpty()) {
                    trySend(sampleCampaigns)
                } else {
                    trySend(campaigns)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun getCampaignById(id: String): DonationCampaign? {
        return try {
            val doc = db.collection("campaigns").document(id).get().await()
            if (doc.exists()) {
                doc.toObject(DonationCampaign::class.java)?.copy(id = doc.id)
            } else {
                sampleCampaigns.find { it.id == id }
            }
        } catch (e: Exception) {
            sampleCampaigns.find { it.id == id }
        }
    }

    override fun getRecentDonors(campaignId: String): Flow<List<Donor>> = callbackFlow {
        val subscription = db.collection("donations")
            .whereEqualTo("campaignId", campaignId)
            .addSnapshotListener { snapshot, error ->
                val donors = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Donor::class.java)
                } ?: emptyList()
                
                if (error != null || donors.isEmpty()) {
                    trySend(
                        listOf(
                            Donor("Budi Santoso", 100000, System.currentTimeMillis() - 3600000),
                            Donor("Siti Aminah", 250000, System.currentTimeMillis() - 7200000),
                            Donor("Ahmad Fauzi", 50000, System.currentTimeMillis() - 14400000)
                        )
                    )
                } else {
                    trySend(donors)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun registerCampaign(campaign: DonationCampaign): Result<Unit> {
        return try {
            db.collection("campaigns").add(campaign).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitDonation(campaignId: String, amount: Long, donorName: String): Result<Unit> {
        return try {
            val donationData = hashMapOf(
                "campaignId" to campaignId,
                "amount" to amount,
                "name" to donorName,
                "timestamp" to System.currentTimeMillis()
            )
            
            val batch = db.batch()
            
            val donationRef = db.collection("donations").document()
            batch.set(donationRef, donationData)
            
            val campaignRef = db.collection("campaigns").document(campaignId)
            batch.update(campaignRef, "collectedAmount", FieldValue.increment(amount))
            batch.update(campaignRef, "donorCount", FieldValue.increment(1))
            
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
