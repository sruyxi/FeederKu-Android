package com.example.feederku.views.livestream.webrtc

import android.content.Context
import android.util.Log

class WebRTCManager(private val context: Context) {

    // Status apakah kamera / siaran sedang aktif
    var isStreaming: Boolean = false
        private set

    private val iceServers = listOf(
        "stun:stun.l.google.com:19302",
        "stun:stun1.l.google.com:19302"
    )

    //Memulai kamera lokal untuk siaran langsung (Streamer)
    fun startLocalCamera() {
        isStreaming = true
        Log.d("WebRTCManager", "Kamera lokal aktif. Menghubungkan ke STUN Server: ${iceServers[0]}")
    }

    //Menghentikan kamera dan menutup sesi siaran
    fun stopCamera() {
        isStreaming = false
        Log.d("WebRTCManager", "Siaran dihentikan, kamera ditutup.")
    }
}