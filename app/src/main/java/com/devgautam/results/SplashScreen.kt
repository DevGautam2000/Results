package com.devgautam.results

/*

        DEVELOPED BY: GAUTAM CHANDRA SAHA
        DATE & TIME: 28/4/21 AT 7:43 PM
        DESCRIPTION: Results Application

*/

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        intentToMain() // intent to main activity
    }

    // function to start an intended activity
    private fun intentToMain() {
        //create Handler with Looper as Handler only is deprecated
        val mHandler = Handler(Looper.getMainLooper())

        //create a new Runnable to delay the intent
        val monitor = Runnable {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        mHandler.postDelayed(monitor, 1000)
    }
}