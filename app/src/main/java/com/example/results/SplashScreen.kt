package com.example.results

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val mHandler = Handler(Looper.getMainLooper())
        val monitor = Runnable {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        mHandler.postDelayed(monitor, 1000)
    }
}