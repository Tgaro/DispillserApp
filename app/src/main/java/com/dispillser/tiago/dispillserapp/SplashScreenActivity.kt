package com.dispillser.tiago.dispillserapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val intentMain = Intent(this, MainActivity::class.java)
        val handle = Handler()
        handle.postDelayed({ startActivity(intentMain) }, 3000)
    }
}