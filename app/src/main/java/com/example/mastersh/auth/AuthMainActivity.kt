package com.example.mastersh.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mastersh.CustomLoading
import com.example.mastersh.R
import com.example.mastersh.auth.model.SessionManager
import com.example.mastersh.auth.presenter.PresenterAuth
import com.example.mastersh.auth.view.AuthView

class AuthMainActivity : AppCompatActivity(), AuthView {
    //deklarasikan variabel session
    private lateinit var sessionManager: SessionManager
    private lateinit var presenterAuth: PresenterAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_main)

        sessionManager = SessionManager(this)
        presenterAuth = PresenterAuth(this)

        val btnAuthLogout = findViewById<Button>(R.id.btnAuthLogout)
        btnAuthLogout.setOnClickListener {
            CustomLoading.showLoading(this)// berguna untuk memulai animasi loading

            val token = sessionManager.fetchAuthToken()
            val token_user = token.toString().trim()
            presenterAuth.logout_user(
                token_user,
            )
        }

        val txtAuthMainToken = findViewById<TextView>(R.id.txtAuthMainToken)
        txtAuthMainToken.text = sessionManager.fetchAuthToken()
    }

    override fun suksesLOGIN(msg: String, token: String) {
        TODO("Not yet implemented")
    }

    override fun errorLOGIN(msg: String) {
        TODO("Not yet implemented")
    }

    override fun suksesREGISTER(msg: String) {
        TODO("Not yet implemented")
    }

    override fun errorREGISTER(msg: String) {
        TODO("Not yet implemented")
    }

    override fun suksesLOGOUT(msg: String) {
        sessionManager.removeAuthToken()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
        startActivity(Intent(this, AuthLoginActivity::class.java))
        finishAffinity()
    }

    override fun errorLOGOUT(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        CustomLoading.hideLoading() // berguna untuk menghentikan animasi loading
    }

    override fun checkValidationRegister(
        nama: String,
        email: String,
        password: String,
        passwordRepeat: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkValidationLogin(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }
}