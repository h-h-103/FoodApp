package com.example.foodapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@Suppress("SameParameterValue")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        colorStatusBar()
        moveFragment()

    }

    private fun moveFragment() {
        // Find the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the BottomNavigationView with NavController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    // Set the status bar icon color based on the current theme
    private fun setStatusBarIconColor(isDarkIcons: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 (API 30) and above
            window.insetsController?.setSystemBarsAppearance (
                if (isDarkIcons) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            // For older versions
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = if (isDarkIcons) {
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    private fun colorStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)
        setStatusBarIconColor(false)
    }
}