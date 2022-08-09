package com.example.imperative.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.imperative.R
import com.example.imperative.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Imperative)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?

        if (navHostFragment != null) {
            val navController = navHostFragment.navController

            binding.bottomNavigation.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->

                when (destination.id) {
                    R.id.homeFragment -> {
                        binding.bottomNavigation.isVisible = true
                    }
                    R.id.detailsFragment -> {
                        binding.bottomNavigation.isVisible = false
                    }
                    R.id.savedFragment -> {
                        binding.bottomNavigation.isVisible = true
                    }
                }
            }
        }



    }


}