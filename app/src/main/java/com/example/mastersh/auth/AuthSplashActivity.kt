package com.example.mastersh.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.mastersh.MainActivity
import com.example.mastersh.R
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.auth.model.Sessions

class AuthSplashActivity : AppCompatActivity() {
    //deklarasikan variabel session
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_splash)

        sessionManager = SessionManager(this)

        // membuat halaman splashscrren menjadi full layar dan menyambunyikan status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE
        // membuat animasi dengan menggabungkan imageview dan animation
        val backgroundImage: ImageView = findViewById(R.id.authSplashLogo)
        val textLogo: TextView = findViewById(R.id.authTextSplash)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)

        backgroundImage.startAnimation(slideAnimation)
        textLogo.startAnimation(slideAnimation)

        // setelah 2 detik ditampilkan splash activity, diarahkan kehalaman menu utama
        Handler().postDelayed({
            checkLogin()
        }, 3000) // lama tampilan splash activity
    }

    private fun checkLogin() {
        val token = sessionManager.fetchAuthToken().toString()
        if (token.isEmpty()) {
            //user belum login
            startActivity(Intent(this, AuthLoginActivity::class.java)) // jika belum login
            //finishAffinity()
        } else {
            //user sudah login
            startActivity(Intent(this, AuthLoginActivity::class.java)) // jika sudah login
            //finishAffinity()
        }
    }
}