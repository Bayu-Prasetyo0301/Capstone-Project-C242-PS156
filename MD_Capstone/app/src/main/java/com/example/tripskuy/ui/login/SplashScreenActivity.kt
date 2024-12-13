package com.example.tripskuy.ui.login

import android.os.Bundle

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tripskuy.MainActivity
import com.example.tripskuy.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (currentUser != null) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, SignInActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)
    }
}
