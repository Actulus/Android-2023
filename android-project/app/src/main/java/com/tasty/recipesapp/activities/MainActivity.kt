package com.tasty.recipesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationBarView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("OnCreate", "onCreate: MainActivity created.")

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboardFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.dashboardFragment)
                    Log.d(TAG, "Home pressed")
                    true
                }

                R.id.recipesFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.recipesFragment)
                    Log.d(TAG, "Recipes pressed")
                    true
                }

                R.id.profileFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.profileFragment)
                    Log.d(TAG, "Profile pressed")
                    true
                }

                else -> false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity onStart() called.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume() called.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity onPause() called.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity onStop() called.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity onDestroy() called.")
    }
}