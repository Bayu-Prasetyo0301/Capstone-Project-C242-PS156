package com.example.tripskuy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.tripskuy.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        if (navHostFragment == null) {
            Log.e("MainActivity", "NavHostFragment is null")
        } else {
            Log.d("MainActivity", "NavHostFragment found")
        }

        val navController = navHostFragment?.navController
        if (navController != null) {

            navView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        Log.d("MainActivity", "Home clicked")
                        navController.navigate(R.id.navigation_home)
                        true
                    }
                    R.id.navigation_favorite -> {
                        Log.d("MainActivity", "Favorite clicked")
                        navController.navigate(R.id.navigation_favorite)
                        true
                    }
                    R.id.navigation_profile -> {
                        Log.d("MainActivity", "Finished clicked")
                        navController.navigate(R.id.navigation_profile)
                        true
                    }
                    else -> false
                }
            }
        } else {
            Log.e("MainActivity", "NavController is null")
        }
    }
}
