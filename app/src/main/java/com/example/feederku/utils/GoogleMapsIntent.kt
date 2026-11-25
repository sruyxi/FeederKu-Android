package com.example.feederku.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openGoogleMapsRoute(
    context: Context,
    latitude: Double,
    longitude: Double,
    mode: String = "w" // "w" = jalan kaki, "l" = motor, "d" = mobil
) {
    val uri = Uri.parse("google.navigation:q=$latitude,$longitude&mode=$mode")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        val webUri = Uri.parse(
            "https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude"
        )
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
        } catch (_: Exception) {
            Toast.makeText(
                context,
                "Tidak dapat membuka Google Maps atau browser",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}