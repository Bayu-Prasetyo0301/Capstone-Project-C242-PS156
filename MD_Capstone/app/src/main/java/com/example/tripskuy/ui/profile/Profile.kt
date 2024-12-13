package com.example.tripskuy.ui.profile

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tripskuy.R
import com.google.firebase.auth.FirebaseAuth

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Pastikan Anda punya metode untuk enableEdgeToEdge
        setContentView(R.layout.activity_profile)

        val firebaseAuth = FirebaseAuth.getInstance()
        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnLogout.setOnClickListener {
            // Logout dari Firebase
            firebaseAuth.signOut()

            // Tutup semua aktivitas dan keluar dari aplikasi
            finishAffinity()
        }
    }
}