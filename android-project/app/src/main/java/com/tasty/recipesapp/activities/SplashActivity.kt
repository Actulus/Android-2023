package com.tasty.recipesapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tasty.recipesapp.R
import com.tasty.recipesapp.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        Log.d(TAG, "SplashActivity onCreate() called.")
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val handlerThread = HandlerThread("SplashHandlerThread", -10)
        handlerThread.start() // Create a Handler on the new HandlerThread
        val handler = Handler(handlerThread.looper)
        val SPLASH_TIME_OUT = 2000L
        handler.postDelayed({
        // Navigate to MainActivity after the delay
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            }, SPLASH_TIME_OUT)
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
