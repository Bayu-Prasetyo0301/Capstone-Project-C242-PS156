package com.example.tripskuy.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tripskuy.MainActivity
import com.example.tripskuy.R
import com.example.tripskuy.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private var isPasswordVisible = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        binding.etOtp.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (binding.etOtp.right - binding.etOtp.compoundDrawables[drawableEnd]?.bounds?.width()!!)
                ) {
                    togglePasswordVisibility()
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
        binding.btnVerify.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {

            binding.etOtp.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD or InputType.TYPE_CLASS_NUMBER
            binding.etOtp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_close, 0) // Ikon mata tertutup
        } else {
            binding.etOtp.inputType = InputType.TYPE_CLASS_NUMBER
            binding.etOtp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_open, 0) // Ikon mata terbuka
        }
        isPasswordVisible = !isPasswordVisible
        binding.etOtp.setSelection(binding.etOtp.text?.length ?: 0)
    }

}
