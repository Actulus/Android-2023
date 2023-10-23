package com.tasty.recipesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tasty.recipesapp.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Log.d(TAG, "SplashActivity onCreate() called.")
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            Log.d(TAG, "Start button clicked.")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", binding.userInputEditText.text.toString())
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "SplashActivity onStart() called.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "SplashActivity onResume() called.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "SplashActivity onPause() called.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "SplashActivity onStop() called.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SplashActivity onDestroy() called.")
    }
}
