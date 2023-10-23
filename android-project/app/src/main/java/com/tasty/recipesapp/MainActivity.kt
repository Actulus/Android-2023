package com.tasty.recipesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val result = intent.getStringExtra("message")
        Log.d(TAG, "Message received: $result")

        binding.userInputTextView.text = result
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